package WizardTD.Delegates;

@FunctionalInterface
public interface Func3<TRet, T1, T2, T3> {
    TRet invoke(T1 ti, T2 t2, T3 t3);
}
