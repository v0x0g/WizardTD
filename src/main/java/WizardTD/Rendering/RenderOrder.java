package WizardTD.Rendering;

import lombok.*;

@AllArgsConstructor
@ToString
public enum RenderOrder implements Comparable<RenderOrder> {
    TILE(0),
    TILE_PRIORITY(1),
    PROJECTILE(2),
    ENTITY(3),
    UI(100)
    ;
    public final int value;
}
