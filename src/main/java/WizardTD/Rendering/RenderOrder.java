package WizardTD.Rendering;

import lombok.*;

@AllArgsConstructor
@ToString
public enum RenderOrder implements Comparable<RenderOrder> {
    NORMAL(0),
    PROJECTILE(2),
    ENTITY(3),
    
    ;
    public final int value;
}
