package WizardTD.Gameplay.Spells;

import WizardTD.Gameplay.Game.*;
import lombok.*;

@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ManaSpell extends Spell{
    public double cost;
    
    @Override
    public void cast(final GameData game) {
        if(game.mana < this.cost) return;
        game.mana -= this.cost;
        // TODO: Need to redo this :(
        game.manaTrickle *= game.config.spell.manaPool.manaGainMultiplier;
        game.manaCap *= game.config.spell.manaPool.manaCapMultiplier;
        this.cost += game.config.spell.manaPool.costIncrease;
    }
}
