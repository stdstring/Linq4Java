/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Functional;

/**
 *
 * @author A.Ushakov
 */
public final class FuncsAdapter {
    public static <TParam, TResult> Func1<TParam, TResult> fakeParam(final Func0<TResult> source) {
        return new Func1<TParam, TResult>() {@Override public TResult func(TParam param) { return source.func(); }};
    }

    public static <TParam1, TParam2, TResult> Func2<TParam1, TParam2, TResult> fakeParams(final Func0<TResult> source) {
        return new Func2<TParam1, TParam2, TResult>() {@Override public TResult func(TParam1 param1, TParam2 param2) { return source.func(); }};
    }

    public static <TParam1, TParam2, TResult> Func2<TParam1, TParam2, TResult> fakeParam1(final Func1<TParam2, TResult> source) {
        return new Func2<TParam1, TParam2, TResult>() {@Override public TResult func(TParam1 param1, TParam2 param2) { return source.func(param2); }};
    }

    public static <TParam1, TParam2, TResult> Func2<TParam1, TParam2, TResult> fakeParam2(final Func1<TParam1, TResult> source) {
        return new Func2<TParam1, TParam2, TResult>() {@Override public TResult func(TParam1 param1, TParam2 param2) { return source.func(param1); }};
    }

    public static <TParam, TResult> Func0<TResult> skipParam(final Func1<TParam, TResult> source, final TParam value) {
        return new Func0<TResult>() {@Override public TResult func() {return source.func(value); }};
    }

    public static <TParam1, TParam2, TResult> Func0<TResult> skipParams(final Func2<TParam1, TParam2, TResult> source, final TParam1 value1, final TParam2 value2) {
        return new Func0<TResult>() {@Override public TResult func() {return source.func(value1, value2); }};
    }

    public static <TParam1, TParam2, TResult> Func1<TParam2, TResult> skipParam1(final Func2<TParam1, TParam2, TResult> source, final TParam1 value1) {
        return new Func1<TParam2, TResult>() {@Override public TResult func(TParam2 param2) {return source.func(value1, param2); }};
    }

    public static <TParam1, TParam2, TResult> Func1<TParam1, TResult> skipParam2(final Func2<TParam1, TParam2, TResult> source, final TParam2 value2) {
        return new Func1<TParam1, TResult>() {@Override public TResult func(TParam1 param1) {return source.func(param1, value2); }};
    }

    public static <TResult> Func0<TResult> fakeResult(final Action0 source, final TResult result) {
        return new Func0<TResult>() {@Override public TResult func() { source.action(); return result; }};
    }

    public static <TParam, TResult> Func1<TParam, TResult> fakeResult(final Action1<TParam> source, final TResult result) {
        return new Func1<TParam, TResult>() {@Override public TResult func(TParam param) { source.action(param); return result; }};
    }

    public static <TParam1, TParam2, TResult> Func2<TParam1, TParam2, TResult> fakeResult(final Action2<TParam1, TParam2> source, final TResult result) {
        return new Func2<TParam1, TParam2, TResult>() {@Override public TResult func(TParam1 param1, TParam2 param2) { source.action(param1, param2); return result; }};
    }

    private FuncsAdapter() {
    }
}
