package WizardTD.Delegates;

import WizardTD.Gameplay.Game.*;
import WizardTD.UI.*;
import processing.core.*;

/**
 * Delegate type for UI actions
 *
 * @param <TElement>
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface UiAction<TElement> extends Action4<TElement, PApplet, GameData, UiState> {}
