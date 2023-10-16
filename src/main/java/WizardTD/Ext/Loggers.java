package WizardTD.Ext;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;
import org.tinylog.*;

@UtilityClass
public class Loggers {
    public final TaggedLogger 
            UI = Logger.tag(LogTag.UI),
            INPUT = Logger.tag(LogTag.INPUT),
            RENDER = Logger.tag(LogTag.RENDER),
            GAMEPLAY = Logger.tag(LogTag.GAMEPLAY),
            EVENT= Logger.tag(LogTag.EVENT);

}
