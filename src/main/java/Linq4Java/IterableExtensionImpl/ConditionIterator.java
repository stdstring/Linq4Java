package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class ConditionIterator<TSource> extends SelectiveIteratorBase<TSource> {

    public ConditionIterator(Iterator<TSource> iterator, Func2<TSource, Integer, Boolean> predicate) {
        super(iterator, predicate);
    }
}