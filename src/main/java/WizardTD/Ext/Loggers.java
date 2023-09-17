package WizardTD.Ext;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;

@UtilityClass
public class Loggers {
    public final @NonNull TaggedLogger 
            UI = Logger.tag(LogTag.UI),
            RENDER = Logger.tag(LogTag.RENDER),
            EVENT= Logger.tag(LogTag.EVENT);

}
