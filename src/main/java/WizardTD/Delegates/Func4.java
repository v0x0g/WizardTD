package WizardTD.Delegates;

@FunctionalInterface
public interface Func4<TRet, T1, T2, T3, T4> {
    TRet invoke(T1 t1, T2 t2, T3 t3, T4 t4);
}
