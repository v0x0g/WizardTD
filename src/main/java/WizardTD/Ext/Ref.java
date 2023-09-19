package WizardTD.Ext;

import lombok.*;
import org.checkerframework.checker.nullness.qual.*;

@EqualsAndHashCode
@AllArgsConstructor
public final class Ref<T> {
    public @Nullable T value;

    @Override
    public @Nullable String toString() {
        return value == null ? "null" : value.toString();
    }
}
