/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionHelper;

import Functional.Func2;
import java.util.Set;

/**
 *
 * @author avu
 */

public final class SetOperationFuncs {
    
    public static <TSource> Func2<TSource, Set<TSource>, Boolean> getExceptOp() {
        return new Func2<TSource, Set<TSource>, Boolean>() {
            @Override
            public Boolean func(TSource param1, Set<TSource> param2) {
                return !param2.contains(param1);
            }
        };
    }
    
    public static <TSource> Func2<TSource, Set<TSource>, Boolean> getIntersectOp() {
        return new Func2<TSource, Set<TSource>, Boolean>() {
            @Override
            public Boolean func(TSource param1, Set<TSource> param2) {
                return param2.contains(param1);
            }
        };
    }

    private SetOperationFuncs() {
    }
}
