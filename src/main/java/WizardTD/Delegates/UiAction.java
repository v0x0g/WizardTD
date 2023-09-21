package WizardTD.Delegates;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;

/**
 * Delegate type for UI actions
 * @param <TElement>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface UiAction<TElement> extends Action3<TElement, GameData, UiState> {}
