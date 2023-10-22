package WizardTD.Gameplay.Spells;

import WizardTD.Gameplay.Game.*;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class ManaSpell extends Spell {
    /**
     * How much the mana pool spell costs to cast.
     */
    public double currentCost;

    @Override
    public void cast(final GameData game) {
        if (game.mana < this.currentCost) return;

        game.mana -= this.currentCost;
        game.manaCap *= game.config.spell.manaPool.manaCapMultiplier;
        this.currentCost += game.config.spell.manaPool.costIncrease;
        // Calculate what the current multiplier is
        double timesActivatedPreviously = (game.manaGainMultiplier - 1.0) / (game.config.spell.manaPool.manaGainMultiplier - 1.0);
        timesActivatedPreviously += 1;
        game.manaGainMultiplier = ((timesActivatedPreviously) * (game.config.spell.manaPool.manaGainMultiplier - 1.0)) + 1;
    }
}
