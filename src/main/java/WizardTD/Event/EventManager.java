package WizardTD.Event;

import io.github.classgraph.*;
import lombok.*;
import lombok.var;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.*;
import java.util.*;

import static org.tinylog.Logger.*;

@UtilityClass
@ExtensionMethod(java.util.Arrays.class)
public class EventManager {

    private final @NonNull HashMap<@NonNull EventType, @NonNull Set<@NonNull EventMethod>> eventsMap = new HashMap<>();

    /**
     * Gets the subscriber list for the given event type
     */
    private @NonNull Set<@NonNull EventMethod> subscriberList(final @NonNull EventType type) {
        return eventsMap.computeIfAbsent(type, $_ -> new HashSet<>());
    }

    /**
     * Invokes the given event
     *
     * @param event The event to invoke
     */
    public void invokeEvent(@NonNull final Event event) {
        debug("start invoke event: {}", event);
        // Ensure we have the entry for corresponding event type
        final Set<@NonNull EventMethod> subscribers = subscriberList(event.eventType);
        for (final EventMethod sub : subscribers) {
            trace("invoke event {} for {}", event, sub);
            sub.processEvent(event);
        }
        debug("done invoke event: {}");
    }

    /**
     * Adds a subscriber to a given event type
     */
    public void subscribe(final @NonNull EventType type, final @NonNull EventMethod method) {
        debug("adding subscriber for {}: {}", type, method);
        subscriberList(type).add(method);
    }

    /**
     * Removes a subscriber from a given event type
     */
    public void unsubscribe(final @NonNull EventType type, final @NonNull EventMethod method) {
        debug("removing subscriber for {}: {}", type, method);
        subscriberList(type).remove(method);
    }

    public void init() {
        info("init event manager");
        // If we wanted to restrict this slightly, we'd do `newClassGraph.acceptPackages("WizardTD")...`
        try (final ScanResult scanResult = new ClassGraph().enableAllInfo().ignoreClassVisibility().ignoreMethodVisibility().scan()) {
            // DO NOT UNCOMMENT THIS LINE
            // Last time I did it dumped 46MiB worth of JSON to stdout
            // and took 34 seconds to complete
            // JUST DON'T, OK
            // trace("WizardTD scanResult={}", scanResult.toJSON(4));
            scanResult
                    // Get classes where events are annotated by @OnEvent
                    .getClassesWithMethodAnnotation(OnEvent.class)
                    // Make parallel
                    .parallelStream()
                    // Map each class to it's methods
                    .map(ClassInfo::getMethodInfo)
                    // Flatmap all methods together
                    .flatMap(Collection::parallelStream)
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

                        trace("method params valid for {}::{}()", meth.getClassName(), meth.getName());
                        return true;
                    })
                    // Map each method to a Key-Value pair of [the method instance] and [the @OnEvent attribute instance]
                    // This loads both the method's class and the attribute's class
                    .map(meth -> new AbstractMap.SimpleImmutableEntry<>(meth.loadClassAndGetMethod(), (OnEvent) meth.getAnnotationInfo(OnEvent.class).loadClassAndInstantiate()))
                    // Iterate over each event method we have
                    .forEach(pair -> {

                        @AllArgsConstructor
                        @ToString
                        class Helper implements EventMethod {
                            public final @NonNull Method method;

                            @Override
                            public void processEvent(@NonNull final Event event) {
                                try {
                                    method.invoke(null, event);
                                } catch (final InvocationTargetException e) {
                                    warn(e, "exception when invoking event method {}", method);
                                    throw new RuntimeException(e);
                                } catch (final IllegalAccessException e) {
                                    error(e, "couldn't invoke event method for {} (Illegal Access)", method);
                                }
                            }
                        }
                        // Loop over each event type this method is subscribed to 
                        // And subscribe it to that event type
                        final OnEvent events = pair.getValue();
                        final Method meth = pair.getKey();
                        // Have to override Java's acess rules, or we get IllegalAccessException on invoking
                        meth.setAccessible(true);
                        events.eventTypes().stream().forEach(eventType -> EventManager.subscribe(eventType, new Helper(meth)));
                    });
        }
        info("done init event manager");
    }

}
