package WizardTD.Delegates;

@FunctionalInterface
public interface Func2<TRet, T1, T2> {
    TRet invoke(T1 t1, T2 t2);
}
