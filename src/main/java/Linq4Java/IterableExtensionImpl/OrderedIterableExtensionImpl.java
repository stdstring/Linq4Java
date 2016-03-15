package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func1;
import Linq4Java.IterableExtension.OrderedIterable;
import Linq4Java.IterableExtension.OrderedIterableExtension;
import Linq4Java.IterableExtensionHelper.ComparatorFactory;
import java.util.Comparator;

/**
 *
 * @author std_string
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
