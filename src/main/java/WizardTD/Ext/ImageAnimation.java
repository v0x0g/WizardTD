package WizardTD.Ext;

import lombok.*;
import processing.core.*;

import java.util.*;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ImageAnimation {
    public final @NonNull List<PImage> frames;
    public final double duration;

    public PImage getImage(final double time) {
        double t = time/duration;
        t = Math.max(0, Math.min(1, t)); // clamp
        final int idx = (int) Math.floor((frames.size() - 1) * t);
        return frames.get(idx);
    }
}
