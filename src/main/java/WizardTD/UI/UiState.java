package WizardTD.UI;

import WizardTD.UI.Elements.*;
import lombok.*;

import java.util.*;

@ToString
@NoArgsConstructor
@EqualsAndHashCode
public final class UiState {

    /**
     * List of all the UI elements that should be rendered
     */
    public final List<UiElement> uiElements = new ArrayList<>();

    /**
     * Flags for what upgrades the user has currently selected
     */
    public boolean
            upgradeRange = false,
            upgradeSpeed = false,
            upgradeDamage = false,
            wantsPlaceTower = false;
}