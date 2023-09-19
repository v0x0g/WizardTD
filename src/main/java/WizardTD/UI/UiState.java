package WizardTD.UI;

import lombok.*;

@ToString
@NoArgsConstructor
@EqualsAndHashCode
public final class UiState {

    /**
     * Flags for controlling the speed of the game
     */
    public boolean
            paused = false,
            fastForward = false;

    /**
     * Flags for what upgrades the user has currently selected
     */
    public boolean
            wantsUpgradeRange = false,
            wantsUpgradeSpeed = false,
            wantsUpgradeDamage3 = false;

    public boolean buildTower = false;
    public boolean manaPool = false;
}