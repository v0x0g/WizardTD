package WizardTD.Gameplay.Game;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

@ToString
@EqualsAndHashCode
@AllArgsConstructor

public final class GameDataConfig {

    /**
     * Config for the towers
     */
    public final TowerConfig tower;

    /**
     * Config for mana
     */
    public final ManaConfig mana;

    /**
     * Config for the spells
     */
    public final SpellConfig spell;

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static final class TowerConfig {

        /**
         * The initial range the towers can fire at (radius in units)
         */
        public final double initialTowerRange;

        /**
         * How many times per second the tower can fire
         */
        public final double initialTowerFiringSpeed;

        /**
         * How much damage the towers do initially
         */
        public final double initialTowerDamage;

        /**
         * Mana cost to build a tower
         */
        public final double towerCost;

    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static final class ManaConfig {

        /**
         * Initial amount of mana the wizard has
         */
        public final double initialManaValue;

        /**
         * Initial maximum amount of mana the wizard can have
         */
        public final double initialManaCap;

        /**
         * Initial gain rate of mana (per second)
         */

        public final double initialManaTrickle;

    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static final class SpellConfig {

        /**
         * Config for the mana pool spell
         */
        public final ManaPool manaPool;

        @ToString
        @EqualsAndHashCode
        @AllArgsConstructor
        public static final class ManaPool {

            /**
             * How much mana the Mana Pool initially costs
             */
            public final double initialCost;

            /**
             * How much the cost of the mana pool should increase by. Stacks additively
             */
            public final double costIncrease;

            /**
             * What multiplier should be applied on the mana gained
             * (applies to mana on enemy kill and trickled mana).
             * Stacks additively (e.g. 1.1 is
             * +10% bonus to mana gained, which would become +20% if
             * the spell is cast twice, or +30% if cast 3 times).
             */
            public final double manaGainMultiplier;

            /**
             * Multiplier for the maximum amount of man the wizard can have.
             * Stacks multiplicatively
             */
            public final double manaCapMultiplier;

        }

    }

}