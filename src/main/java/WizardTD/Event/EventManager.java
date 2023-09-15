package WizardTD.Event;

import io.github.classgraph.*;
import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

import java.util.*;

import static org.tinylog.Logger.*;

@UtilityClass
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
    public void subscribe(final EventType type, final EventMethod method) {
        debug("adding subscriber for {}: {}", type, method);
        subscriberList(type).add(method);
    }

    /**
     * Removes a subscriber from a given event type
     */
    public void unsubscribe(final EventType type, final EventMethod method) {
        debug("removing subscriber for {}: {}", type, method);
        subscriberList(type).remove(method);
    }

    public void init() {
        info("init event manager");
        try (final ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages("WizardTD").scan()) {
            info("scanResult={}", scanResult.toJSON(2));
            // List of classes that have [methods annotated with @OnEvents]
            final ClassInfoList classesWithEventMethodsList = scanResult.getClassesWithMethodAnnotation(OnEvents.class);
            info("classInfo={}", classesWithEventMethodsList);

//            for (final ClassInfo eventClassInfo : classesWithEventMethodsList) {
//                info("classInfo={}", eventClassInfo);
//                // Get the Route annotation on the class
//                final AnnotationInfo annotationInfo = eventClassInfo.getAnnotationInfo(OnEvents.class);
//                final AnnotationParameterValueList paramVals = annotationInfo.getParameterValues();
//
////                // The OnEvent annotation has a parameter named "event"
////                final String routePath = paramVals.get("event");
//
//                // Alternatively, you can load and instantiate the annotation, so that the annotation
//                // methods can be called directly to get the annotation parameter values (this sets up
//                // an InvocationHandler to emulate the Route annotation instance, since annotations
//                // can't be instantiated directly without also loading the annotated class).
//                final OnEvents route = (OnEvents) annotationInfo.loadClassAndInstantiate();
//                final OnEvent[] events = route.value();
//
//                // ...
//            }
        }
        info("done init event manager");
    }

}
