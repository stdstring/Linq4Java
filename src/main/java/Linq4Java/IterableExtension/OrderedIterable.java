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
public interface OrderedIterable<TSource> extends Iterable<TSource> {
    <TKey> OrderedIterable<TSource> createOrderedIterable(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer, boolean descending);
}
