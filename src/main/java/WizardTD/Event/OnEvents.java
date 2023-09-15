package WizardTD.Event;

import org.checkerframework.checker.nullness.qual.*;

import java.lang.annotation.*;

/**
 * Attribute to mark a method as subscribing to multiple events
 */
@Retention(RetentionPolicy.RUNTIME) // Read via reflection
@Target(ElementType.METHOD)
public @interface OnEvents {

    @NonNull OnEvent @NonNull [] value();

}