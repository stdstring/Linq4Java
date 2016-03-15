package Linq4Java.IterableExtension;

/**
 *
 * @author std_string
 */
public interface Grouping<TKey, TElement> extends Iterable<TElement> {
    TKey getKey();
}
