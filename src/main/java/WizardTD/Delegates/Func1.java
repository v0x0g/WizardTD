package WizardTD.Delegates;

@FunctionalInterface
public interface Func1<TRet, T1> {
    TRet invoke(T1 t1);
}
