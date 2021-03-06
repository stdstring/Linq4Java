package Linq4Java.IterableExtensionHelper;

import Linq4Java.Functional.Func1;

/**
 *
 * @author std_string
 */
public final class TrivialFuncs {

    public static <TSource> Func1<TSource, Boolean> getTrivialPredicate() {
        return new Func1<TSource, Boolean>(){@Override public Boolean func(TSource param) { return true; }};
    }

    public static <TSource> Func1<TSource, TSource> getTrivialSelector() {
        return new Func1<TSource, TSource>(){@Override public TSource func(TSource param) { return param; }};
    }

    private TrivialFuncs() {
    }
}
