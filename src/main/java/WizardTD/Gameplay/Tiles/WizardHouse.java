package WizardTD.Gameplay.Tiles;

import lombok.*;
import org.checkerframework.checker.nullness.qual.*;
import processing.core.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class WizardHouse extends Tile {

    /**
     * How much mana the wizard has remaining
     */
    public double mana;

    public double manaCap;

    public double manaTrickle;


    public static @Nullable PImage tileImage = null;

    @Override
    public @Nullable PImage getImage() {
        return tileImage;
    }
}