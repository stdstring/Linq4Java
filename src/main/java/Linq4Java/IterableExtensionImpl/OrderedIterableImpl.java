/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import Functional.Func0;
import Functional.Func1;
import IterableExtension.OrderedIterable;
import IterableExtensionHelper.ComparatorFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author aushakov
 */
class OrderedIterableImpl<TSource> implements OrderedIterable<TSource> {

    @Override
    public <TKey> OrderedIterable<TSource> createOrderedIterable(final Func1<TSource, TKey> keySelector, final Comparator<TKey> comparator, final boolean descending) {
        Func0<Iterable<Iterable<TSource>>> factory = new Func0<Iterable<Iterable<TSource>>>() {
            @Override
            public Iterable<Iterable<TSource>> func() {
                Comparator<TKey> keyComparator = descending ? ComparatorFactory.createDescending(comparator) : comparator;
                Comparator<TSource> comparerByKey = ComparatorFactory.<TSource, TKey>create(keySelector, keyComparator);
                return OrderHelper.order(sourceFactory.func(), comparerByKey);
            }
        };
        return new OrderedIterableImpl(factory);
    }

    @Override
    public Iterator<TSource> iterator() {
        return new ConcatIterator(sourceFactory.func().iterator());
    }
    
    public static <TSource, TKey> OrderedIterable<TSource> createOrderedIterable(Iterable<TSource> source, Func1<TSource, TKey> keySelector, Comparator<TKey> comparator) {
        Comparator<TSource> comparatorByKey = ComparatorFactory.create(keySelector, comparator);
        return createOrderedIterable(source, comparatorByKey);
    }
    
    public static <TSource> OrderedIterable<TSource> createOrderedIterable(final Iterable<TSource> source, final Comparator<TSource> comparator) {
        Func0<Iterable<Iterable<TSource>>> factory = new Func0<Iterable<Iterable<TSource>>>() {
            @Override
            public Iterable<Iterable<TSource>> func() {
                List<Iterable<TSource>> group = new ArrayList<Iterable<TSource>>();
                group.add(source);
                return OrderHelper.order(group, comparator);
            }
        };
        return new OrderedIterableImpl(factory);
    }
    
    private OrderedIterableImpl(Func0<Iterable<Iterable<TSource>>> factory) {
        this.sourceFactory = factory;
    }
    
    private final Func0<Iterable<Iterable<TSource>>> sourceFactory;
}