package WizardTD.Delegates;

import WizardTD.*;
import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;

/**
 * Delegate type for UI actions
 *
 * @param <TElement>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface UiAction<TElement> extends Action4<TElement, App, GameData, UiState> {}
