package WizardTD;

import lombok.experimental.*;

import java.util.*;

@UtilityClass
@ExtensionMethod(Arrays.class)
public class TestResources {
    public final static String BASE_DIR = "src/test/resources/WizardTD/";

    public final static String CFG_DIR = "config/";
    public final static String LVL_DIR = "levels/";
    public final static String FULL_CFG_DIR = "full/";

    /**
     * Array of paths to valid config files
     */
    public final static String[] VALID_CONFIGS = paths(
            CFG_DIR,
            "valid_config_01.json",
            "valid_config_02.json"
    );
    /**
     * Array of paths to invalid config files
     */
    public final static String[] INVALID_CONFIGS = paths(
            CFG_DIR,
            "invalid_config_01.json",
            "invalid_config_02.json"
    );
    /**
     * Array of paths to valid levels
     */
    public final static String[] VALID_LEVELS = paths(
            LVL_DIR,
            "valid_level_01.txt",
            "valid_level_02.txt"
    );

    /**
     * Array of paths to invalid levels
     */
    public final static String[] INVALID_LEVELS = paths(
            LVL_DIR,
            "invalid_level_01.txt",
            "invalid_level_02.txt",
            "invalid_level_03.txt",
            "invalid_level_04.txt",
            "invalid_level_05.txt",
            "invalid_level_06.txt"
    );

    private static String[] paths(final String dir, String... paths) {
        return paths.stream().map(s -> BASE_DIR + dir + s).toArray(String[]::new);
    }
}
