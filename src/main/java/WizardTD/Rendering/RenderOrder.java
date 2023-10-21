package WizardTD.Rendering;

import lombok.*;

@AllArgsConstructor
@ToString
public enum RenderOrder implements Comparable<RenderOrder> {
    TILE(0),
    PROJECTILE(2),
    ENTITY(3),
    TILE_PRIORITY(4),
    BACKGROUND(101),
    UI(102);
    public final int value;
}
