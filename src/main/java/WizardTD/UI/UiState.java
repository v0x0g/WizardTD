package WizardTD.UI;

import WizardTD.UI.Elements.*;
import lombok.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

@ToString
@NoArgsConstructor
@EqualsAndHashCode
public final class UiState {

    /**
     * Flags for what upgrades the user has currently selected
     */
    public boolean
            wantsUpgradeRange = false,
            wantsUpgradeSpeed = false,
            wantsUpgradeDamage = false;

    public boolean buildTower = false;
    public boolean manaPool = false;

    /**
     * List of all the UI elements that should be rendered
     */
    public final List<UiElement> uiElements         = new ArrayList<>();
}