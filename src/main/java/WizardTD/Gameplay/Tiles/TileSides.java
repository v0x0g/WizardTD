package WizardTD.Gameplay.Tiles;

import lombok.*;

@SuppressWarnings("PointlessBitwiseExpression")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TileSides {
    LEFT(1 << 0), RIGHT(1 << 1), TOP(1 << 2), BOTTOM(1 << 3),
    LEFT_RIGHT(LEFT.value | RIGHT.value), TOP_BOTTOM(TOP.value | BOTTOM.value),
    LEFT_RIGHT_BOTTOM(LEFT_RIGHT.value | BOTTOM.value), LEFT_RIGHT_TOP(LEFT_RIGHT.value | TOP.value),
    ALL_SIDES(LEFT_RIGHT.value | TOP_BOTTOM.value) 
    ;

    private final int value;
}
