/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Functional;

/**
 *
 * @author A.Ushakov
 */
public class FuncsCastAdapter {
    
    public static <TInputResult, TOutputResult> Func0<TOutputResult> cast(final Func0<TInputResult> source) {
        return new Func0<TOutputResult>() {
            @Override
            public TOutputResult func() {
                return (TOutputResult)source.func();
            }
        };
    }
    
    public static <TParam, TInputResult, TOutputResult> Func1<TParam, TOutputResult> cast(final Func1<TParam, TInputResult> source) {
        return new Func1<TParam, TOutputResult>() {
            @Override
            public TOutputResult func(TParam param) {
                return (TOutputResult)source.func(param);
            }
        };
    }
    
    public static <TParam1, TParam2, TInputResult, TOutputResult> Func2<TParam1, TParam2, TOutputResult> cast(final Func2<TParam1, TParam2, TInputResult> source) {
        return new Func2<TParam1, TParam2, TOutputResult>() {
            @Override
            public TOutputResult func(TParam1 param1, TParam2 param2) {
                return (TOutputResult)source.func(param1, param2);
            }
        };
    }
    
    private FuncsCastAdapter() {        
    }
}
