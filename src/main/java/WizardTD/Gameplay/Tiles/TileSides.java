package WizardTD.Gameplay.Tiles;

import lombok.*;

@SuppressWarnings("PointlessBitwiseExpression")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public enum TileSides {
    LEFT(1 << 0), RIGHT(1 << 1), UP(1 << 2), DOWN(1 << 3);

    public final int value;
}
