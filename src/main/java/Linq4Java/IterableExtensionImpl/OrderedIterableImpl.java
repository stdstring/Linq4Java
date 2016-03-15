package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import Linq4Java.Functional.Func1;
import Linq4Java.IterableExtension.OrderedIterable;
import Linq4Java.IterableExtensionHelper.ComparatorFactory;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author std_string
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