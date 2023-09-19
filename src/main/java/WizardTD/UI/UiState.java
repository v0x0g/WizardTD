package WizardTD.UI;

import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

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
    
    public final @NonNull List<UiElement> uiElements = new ArrayList<>();
}