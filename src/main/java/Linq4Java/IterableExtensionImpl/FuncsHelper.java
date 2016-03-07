/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func2;
import Functional.Tuple2;
import IterableExtension.Grouping;

/**
 *
 * @author avu
 */
final class FuncsHelper {
    
    // адаптер для объединения в наборе исходного элемента и элемента набора
    static <TSource, TCollection> Func2<TSource, Integer, Iterable<Tuple2<TSource, TCollection>>> unitingAdapter(final Func2<TSource, Integer, Iterable<TCollection>> collectionSelector) {
        return new Func2<TSource, Integer, Iterable<Tuple2<TSource, TCollection>>>() {
            @Override
            public Iterable<Tuple2<TSource, TCollection>> func(final TSource param, Integer index) {
                Iterable<TCollection> iterable = collectionSelector.func(param, index);
                return new TransformIterable(iterable, getUnitingFunc(param));
            }
        };
    }

    // адаптер для разъединения пары элементов и получения при помощи них результирующего элемента
    static <TSource, TCollection, TResult> Func2<Tuple2<TSource, TCollection>, Integer, TResult> disjunctiveAdapter(final Func2<TSource, TCollection, TResult> resultSelector) {
        return new Func2<Tuple2<TSource, TCollection>, Integer, TResult>() {
            @Override
            public TResult func(Tuple2<TSource, TCollection> param, Integer index) {
                return resultSelector.func(param.getItem1(), param.getItem2());
            }
        };
    }

    static <TKey, TElement, TResult> Func2<Grouping<TKey, TElement>, Integer, TResult> groupResultSelectorAdapter(final Func2<TKey, Iterable<TElement>, TResult> resultSelector) {
        return new Func2<Grouping<TKey, TElement>, Integer, TResult>() {
            @Override
            public TResult func(Grouping<TKey, TElement> param, Integer index) {
                return resultSelector.func(param.getKey(), param);
            }
        };
    }

    // вспомогательный функтор для объединения исходного и результирующего элементов
    private static <TResult, TSource> Func2<TResult, Integer, Tuple2<TSource, TResult>> getUnitingFunc(final TSource sourceItem) {
        return new Func2<TResult, Integer, Tuple2<TSource, TResult>>() {
            @Override
            public Tuple2<TSource, TResult> func(TResult resultParam, Integer resultIndex) {
                return new Tuple2<TSource, TResult>(sourceItem, resultParam);
            }
        };
    }

    private FuncsHelper() {
    }
}
