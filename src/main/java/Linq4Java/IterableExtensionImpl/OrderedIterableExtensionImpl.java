/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import Functional.Func1;
import IterableExtension.OrderedIterable;
import IterableExtension.OrderedIterableExtension;
import IterableExtensionHelper.ComparatorFactory;
import java.util.Comparator;

/**
 *
 * @author A.Ushakov
 */
class OrderedIterableExtensionImpl<TSource> extends IterableExtensionImpl<TSource> implements OrderedIterableExtension<TSource> {
    
    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector) {
         return createOrderedIterable(keySelector, ComparatorFactory.<TKey>create(true), false);
    }

    @Override
    public <TKey> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return createOrderedIterable(keySelector, comparer, false);
    }

    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector) {
        return createOrderedIterable(keySelector, ComparatorFactory.<TKey>create(true), true);
    }

    @Override
    public <TKey> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return createOrderedIterable(keySelector, comparer, true);
    }

    @Override
    public <TKey> OrderedIterableExtension<TSource> createOrderedIterable(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer, boolean descending) {
        return new OrderedIterableExtensionImpl(orderedIterable.createOrderedIterable(keySelector, comparer, descending));
    }
    
    OrderedIterableExtensionImpl(OrderedIterable<TSource> orderedIterable){
        super(orderedIterable);
        this.orderedIterable = orderedIterable;
    }

    private final OrderedIterable<TSource> orderedIterable;
}
