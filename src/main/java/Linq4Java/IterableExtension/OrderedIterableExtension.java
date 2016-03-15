package Linq4Java.IterableExtension;

import Linq4Java.Functional.Func1;
import java.util.Comparator;

/**
 *
 * @author std_string
 */
public interface OrderedIterableExtension<TSource> extends IterableExtension<TSource>, OrderedIterable<TSource> {
    // thenBy ...
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> thenBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
    // thenByDescending
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> thenByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
}
