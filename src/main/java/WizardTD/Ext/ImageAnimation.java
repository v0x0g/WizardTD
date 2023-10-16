package WizardTD.Ext;

import lombok.*;
import processing.core.*;

import java.util.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ImageAnimation {
    public @NonNull List<PImage> frames;
    public double duration;
    
    public PImage getImage(final double time){
        double t = time / duration;
        t = Math.max(0, Math.min(1, t));
        return frames.get((int)Math.floor(frames.size() * t));
    }
}
