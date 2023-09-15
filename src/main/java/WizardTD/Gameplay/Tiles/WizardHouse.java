package WizardTD.Gameplay.Tiles;

import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = false)
public final class WizardHouse extends Tile {

    /**
     * How much mana the wizard has remaining
     */
    public double mana;

    public double manaCap;

    public double manaTrickle;

}