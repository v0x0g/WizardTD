package WizardTD.Event;

import WizardTD.Ext.*;
import io.github.classgraph.*;
import lombok.*;
import lombok.var;
import lombok.experimental.*;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

import static org.tinylog.Logger.*;

@UtilityClass
@ExtensionMethod(java.util.Arrays.class)
public class EventManager {

    private final ConcurrentHashMap<EventType, Set<EventMethod>> eventsMap =
            new ConcurrentHashMap<>();

    /**
     * Gets the subscriber list for the given event type
     */
    private Set<EventMethod> subscriberList(final EventType type) {
        // Use a concurrent HashMap's key set to get a concurrent set
        return eventsMap.computeIfAbsent(type, $_ -> ConcurrentHashMap.newKeySet());
    }

    /**
     * Invokes the given event
     *
     * @param event The event to invoke
     */
    public void invokeEvent(final Event event) {
        Loggers.EVENT.debug("start invoke event: {}", event);
        // Ensure we have the entry for corresponding event type
        final Set<EventMethod> subscribers = subscriberList(event.eventType);
        subscribers
                .stream()
                .forEach(sub -> {
                    Loggers.EVENT.trace("invoking event: {} for {}", event, sub);
                    sub.processEvent(event);
                });

        Loggers.EVENT.debug("done invoke event: {}", event);
    }

    /**
     * Adds a subscriber to a given event type
     */
    public void subscribe(final EventType type, final EventMethod method) {
        Loggers.EVENT.debug("adding subscriber for {}: {}", type, method);
        subscriberList(type).add(method);
    }

    /**
     * Removes a subscriber from a given event type
     */
    public void unsubscribe(final EventType type, final EventMethod method) {
        Loggers.EVENT.debug("removing subscriber for {}: {}", type, method);
        subscriberList(type).remove(method);
    }

    public void init() {
        info("init event manager");
        // If we wanted to restrict this slightly, we'd do `newClassGraph.acceptPackages("WizardTD")...`
        // But I don't care lol
        try (final ScanResult scanResult = new ClassGraph().enableAllInfo()
                                                           .ignoreClassVisibility()
                                                           .ignoreMethodVisibility()
                                                           .scan()) {
            // DO NOT UNCOMMENT THIS LINE
            // Last time I did it dumped 46MiB worth of JSON to stdout
            // and took 34 seconds to complete
            // JUST DON'T, OK
            // trace("WizardTD scanResult={}", scanResult.toJSON(4));
            scanResult
                    // Get classes where events are annotated by @OnEvent
                    .getClassesWithMethodAnnotation(OnEvent.class)
                    .stream()
                    // Map each class to it's methods
                    .map(ClassInfo::getMethodInfo)
                    // Flatmap all methods together
                    .flatMap(Collection::stream)
                    // Filter to all @OnEvent methods
                    .filter(meth -> meth.hasAnnotation(OnEvent.class))
                    // Ensure all methods are static
                    .filter(meth -> {
                        if (meth.isStatic()) return true;
                        warn("expected static event method but was instance: {}", meth);
                        return false;
                    })
                    // Do the same to validate method parameters
                    .filter(meth -> {
                        final var params = meth.getParameterInfo();
                        if (params.length != 1) {
                            warn("incorrect number of params ({}) for method {}", meth);
                            return true;
                        }
                        if (!(params[0].getTypeDescriptor() instanceof ClassRefTypeSignature)) {
                            warn("param not a class reference for method {}", meth);
                            return true;
                        }
                        final ClassRefTypeSignature paramType = (ClassRefTypeSignature) params[0].getTypeDescriptor();
                        // Try and load the class instance associated with the parameter
                        // And then compare it with the class of our event type
                        // To avoid loading the class when not necessary, check class names first as a short-circuit
                        final Class<?> eventClass = Event.class;
                        final String eventClassName = eventClass.getName();
                        final String paramClassName = paramType.getBaseClassName();
                        if (!Objects.equals(paramClassName, eventClassName) && paramType.loadClass() != eventClass) {
                            warn("method {} param type is not {} (was {})", meth, eventClassName, paramClassName);
                            return false;
                        }

                        trace("method params valid for {}.{}()", meth.getClassName(), meth.getName());
                        return true;
                    })
                    // Map each method to a Key-Value pair of [the method instance] and [the @OnEvent attribute instance]
                    // This loads both the method's class and the attribute's class
                    .map(meth -> new AbstractMap.SimpleImmutableEntry<>(
                            meth.loadClassAndGetMethod(),
                            (OnEvent) meth.getAnnotationInfo(OnEvent.class)
                                          .loadClassAndInstantiate()
                    ))
                    // Iterate over each event method we have
                    .forEach(pair -> {

                        @AllArgsConstructor
                        @ToString
                        class Helper implements EventMethod {
                            public final Method method;

                            @Override
                            public void processEvent(final Event event) {
                                try {
                                    method.invoke(null, event);
                                }
                                catch (final InvocationTargetException e) {
                                    warn(e, "exception when invoking event method {}", method);
                                }
                                catch (final IllegalAccessException e) {
                                    error(e, "couldn't invoke event method for {} (Illegal Access)", method);
                                }
                            }
                        }
                        // Loop over each event type this method is subscribed to 
                        // And subscribe it to that event type
                        final OnEvent events = pair.getValue();
                        final Method meth = pair.getKey();
                        // Have to override Java's access rules, or we get IllegalAccessException on invoking
                        meth.setAccessible(true);
                        events.eventTypes()
                              .stream()
                              .forEach(eventType -> EventManager.subscribe(eventType, new Helper(meth)));
                    });
        }
        info("done init event manager");
    }

}
