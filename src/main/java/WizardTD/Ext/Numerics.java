package WizardTD.Ext;

import lombok.experimental.*;

@UtilityClass
public class Numerics {
    public double inverseLerp(final double v, final double a, final double b) {
        return (v - a) / (b - a);
    }

    public double lerp(final double a, final double b, final double t) {
        return (a * t) + (b * (1.0 - t));
    }
}