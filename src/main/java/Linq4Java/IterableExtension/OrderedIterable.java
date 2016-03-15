package Linq4Java.IterableExtension;

import Linq4Java.Functional.Func1;
import java.util.Comparator;

/**
 *
 * @author std_string
 */
public interface OrderedIterable<TSource> extends Iterable<TSource> {
    <TKey> OrderedIterable<TSource> createOrderedIterable(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer, boolean descending);
}
