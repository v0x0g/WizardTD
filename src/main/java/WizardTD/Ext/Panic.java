package WizardTD.Ext;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;

/**
 * See rust panic
 */
@StandardException 
public class Panic extends RuntimeException{

    public Panic(final @Nullable String message, final @NonNull Throwable cause) {
        super(message, cause);
        Logger.error(this);
        System.exit(69);
    }
}
