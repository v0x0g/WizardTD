package WizardTD.Rendering;

import lombok.*;

@AllArgsConstructor
@ToString
public enum RenderOrder implements Comparable<RenderOrder> {
    TILE(0),
    ENTITY(2),
    PROJECTILE(3),
    
    WIZARD_HOUSE(4),
    
    TOWER_RANGE_INDICATOR(100),
    UI_BACKGROUND(101),
    UI(102)
    ;
    public final int value;
}
