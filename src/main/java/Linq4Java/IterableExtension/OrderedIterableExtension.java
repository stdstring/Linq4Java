/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtension;

import Functional.Func1;
import java.util.Comparator;

/**
 *
 * @author A.Ushakov
 */
public interface OrderedIterableExtension<TSource> extends IterableExtension<TSource>, OrderedIterable<TSource> {
    // thenBy ...
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
    // thenByDescending
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
}
