package WizardTD.UI;

import lombok.experimental.*;
import org.checkerframework.checker.nullness.qual.*;

@UtilityClass
public class Resources {
    public static final String BASE_DIR = "src/main/resources/WizardTD";
    @UtilityClass
    public static class Tiles {
        public static final String DIR = Resources.BASE_DIR + "/Tiles";
        @UtilityClass
        public static class Grass {
            public static final String DIR       = Tiles.DIR + "/Grass";
            public static final String ONLY_TILE = DIR + "/grass.png";
        }
        @UtilityClass
        public static class Path {
            public static final String DIR       = Tiles.DIR + "/Path";
            public static final String STRAIGHT  = DIR + "/path0.png";
            public static final String CORNER    = DIR + "/path1.png";
            public static final String THREE_WAY = DIR + "/path2.png";
            public static final String FOUR_WAY  = DIR + "/path3.png";
        }
        @UtilityClass
        public static class Shrub {
            public static final String DIR       = Tiles.DIR + "/Shrub";
            public static final String ONLY_TILE = DIR + "/shrub.png";
        }
        @UtilityClass
        public static class WizardHouse {
            public static final String DIR       = Tiles.DIR + "/WizardHouse";
            public static final String ONLY_TILE = DIR + "/wizard_house.png";
        }
        @UtilityClass
        public static class Tower {
            public static final String DIR   = Tiles.DIR + "/Tower";
            public static final String Tile0 = DIR + "/tower0.png";
            public static final String Tile1 = DIR + "/tower1.png";
            public static final String Tile2 = DIR + "/tower2.png";
        }
    }
}
