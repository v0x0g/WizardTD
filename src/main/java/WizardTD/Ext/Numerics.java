package WizardTD.Ext;

import lombok.experimental.*;

@UtilityClass
public class Numerics {
    public double inverseLerp(final double v, final double a, final double b){
        return (v - a) / (b - a);
    }
}