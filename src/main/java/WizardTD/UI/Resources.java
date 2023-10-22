package WizardTD.UI;

import lombok.experimental.*;

@UtilityClass
public class Resources {
    public static final String BASE_DIR = "src/main/resources/WizardTD";
    @UtilityClass
    public static class Tiles {
        public static final String DIR = Resources.BASE_DIR + "/Tiles";
        @UtilityClass
        public static class Grass {
            public static final String DIR = Tiles.DIR + "/Grass";
            public static final String ONLY_TILE = DIR + "/grass.png";
        }
        @UtilityClass
        public static class Path {
            public static final String DIR = Tiles.DIR + "/Path";
            public static final String STRAIGHT = DIR + "/path0.png";
            public static final String CORNER = DIR + "/path1.png";
            public static final String THREE_WAY = DIR + "/path2.png";
            public static final String FOUR_WAY = DIR + "/path3.png";
        }
        @UtilityClass
        public static class Shrub {
            public static final String DIR = Tiles.DIR + "/Shrub";
            public static final String ONLY_TILE = DIR + "/shrub.png";
        }
        @UtilityClass
        public static class WizardHouse {
            public static final String DIR = Tiles.DIR + "/WizardHouse";
            public static final String ONLY_TILE = DIR + "/wizard_house.png";
        }
        @UtilityClass
        public static class Tower {
            public static final String DIR = Tiles.DIR + "/Tower";
            public static final String Tile0 = DIR + "/tower0.png";
            public static final String Tile1 = DIR + "/tower1.png";
            public static final String Tile2 = DIR + "/tower2.png";
        }
    }
    @UtilityClass
    public static class Enemies {
        public static final String DIR = Resources.BASE_DIR + "/Enemies";
        @UtilityClass
        public static class Beetle {
            public static final String DIR = Enemies.DIR + "/Beetle";
            public static final String ONLY_TILE = DIR + "/beetle.png";
        }
        @UtilityClass
        public static class Gremlin {
            public static final String DIR = Enemies.DIR + "/Gremlin";
            public static final String NORMAL = DIR + "/gremlin.png";
            public static final String DYING_1 = DIR + "/gremlin1.png";
            public static final String DYING_2 = DIR + "/gremlin2.png";
            public static final String DYING_3 = DIR + "/gremlin3.png";
            public static final String DYING_4 = DIR + "/gremlin4.png";
            public static final String DYING_5 = DIR + "/gremlin5.png";
        }
        @UtilityClass
        public static class Worm {
            public static final String DIR = Enemies.DIR + "/Worm";
            public static final String ONLY_TILE = DIR + "/worm.png";
        }
    }

    @UtilityClass
    public static class Projectiles {
        public static final String DIR = Resources.BASE_DIR + "/Projectiles";
        @UtilityClass
        public static class Fireball {
            public static final String DIR = Projectiles.DIR + "/Fireball";
            public static final String PROJECTILE_IMAGE = DIR + "/fireball.png";
            public static final String CROSSHAIR_IMAGE = DIR + "/crosshair.png";
        }
    }
}
