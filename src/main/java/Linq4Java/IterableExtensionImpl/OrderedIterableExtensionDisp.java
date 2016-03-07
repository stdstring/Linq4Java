/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func1;
import IterableExtension.OrderedIterable;
import IterableExtension.OrderedIterableExtension;
import java.util.Comparator;

import static IterableExtensionImpl.OrderedIterableExtensionImplSelector.getImpl;

/**
 *
 * @author aushakov
 */
/*class OrderedIterableExtensionDisp<TSource> extends IterableExtensionDisp<TSource> implements OrderedIterableExtension<TSource> {

    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector) {
        return new OrderedIterableExtensionDisp(getImpl((OrderedIterable<TSource>)getIterable()).thenBy(keySelector));
    }

    @Override
    public <TKey> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return new OrderedIterableExtensionDisp(getImpl((OrderedIterable<TSource>)getIterable()).thenBy(keySelector, comparer));
    }

    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector) {
        return new OrderedIterableExtensionDisp(getImpl((OrderedIterable<TSource>)getIterable()).thenByDescending(keySelector));
    }

    @Override
    public <TKey> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return new OrderedIterableExtensionDisp(getImpl((OrderedIterable<TSource>)getIterable()).thenByDescending(keySelector, comparer));
    }

    @Override
    public <TKey> OrderedIterable<TSource> createOrderedIterable(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer, boolean descending) {
        return new OrderedIterableExtensionDisp(getImpl((OrderedIterable<TSource>)getIterable()).createOrderedIterable(keySelector, comparer, descending));
    }
    
    OrderedIterableExtensionDisp(OrderedIterable<TSource> orderedIterable) {
        super(orderedIterable);
    }
}*/
