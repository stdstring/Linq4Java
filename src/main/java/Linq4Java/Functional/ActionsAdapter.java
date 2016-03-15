package Linq4Java.Functional;

/**
 *
 * @author std_string
 */
public final class ActionsAdapter {

    public static <TParam> Action1<TParam> fakeParam(final Action0 source) {
        return new Action1<TParam>() {@Override public void action(TParam param) { source.action(); }};
    }

    public static <TParam1, TParam2> Action2<TParam1, TParam2> fakeParams(final Action0 source) {
        return new Action2<TParam1, TParam2>() {@Override public void action(TParam1 param1, TParam2 param2) { source.action(); }};
    }

    public static <TParam1, TParam2> Action2<TParam1, TParam2> fakeParam1(final Action1<TParam2> source) {
        return new Action2<TParam1, TParam2>() {@Override public void action(TParam1 param1, TParam2 param2) { source.action(param2); }};
    }

    public static <TParam1, TParam2> Action2<TParam1, TParam2> fakeParam2(final Action1<TParam1> source) {
        return new Action2<TParam1, TParam2>() {@Override public void action(TParam1 param1, TParam2 param2) { source.action(param1); }};
    }

    public static <TParam> Action0 skipParam(final Action1<TParam> source, final TParam value) {
        return new Action0() {@Override public void action() { source.action(value); }};
    }

    public static <TParam1, TParam2> Action0 skipParams(final Action2<TParam1, TParam2> source, final TParam1 value1, final TParam2 value2) {
        return new Action0() {@Override public void action() { source.action(value1, value2); }};
    }

    public static <TParam1, TParam2> Action1<TParam2> skipParam1(final Action2<TParam1, TParam2> source, final TParam1 value1) {
        return new Action1<TParam2>() {@Override public void action(TParam2 param2) { source.action(value1, param2); }};
    }

    public static <TParam1, TParam2> Action1<TParam1> skipParam2(final Action2<TParam1, TParam2> source, final TParam2 value2) {
        return new Action1<TParam1>() {@Override public void action(TParam1 param1) { source.action(param1, value2); }};
    }

    public static <TResult> Action0 skipResult(final Func0<TResult> source) {
        return new Action0() {@Override public void action() { source.func(); }};
    }

    public static <TParam, TResult> Action1<TParam> skipResult(final Func1<TParam, TResult> source) {
        return new Action1<TParam>() {@Override public void action(TParam param) { source.func(param); }};
    }

    public static <TParam1, TParam2, TResult> Action2<TParam1, TParam2> skipResult(final Func2<TParam1, TParam2, TResult> source) {
        return new Action2<TParam1, TParam2>() {@Override public void action(TParam1 param1, TParam2 param2) { source.func(param1, param2); }};
    }

    private ActionsAdapter() {
    }
}
