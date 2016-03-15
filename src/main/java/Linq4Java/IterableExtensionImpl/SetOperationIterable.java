package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author std_string
 */
final class SetOperationIterable<TSource> implements Iterable<TSource> {

    public SetOperationIterable(Iterable<TSource> source,
                                Iterable<TSource> other,
                                Func2<TSource, Set<TSource>, Boolean> setOperation) {
        this.source = new DistinctIterable(source);
        this.other = new DistinctIterable(other);
        this.setOperation = setOperation;
    }

    @Override
    public Iterator<TSource> iterator() {
        return new SetOperationIterator(source.iterator(), other, setOperation);
    }

    private final Iterable<TSource> source;
    private final Iterable<TSource> other;
    private final Func2<TSource, Set<TSource>, Boolean> setOperation;
}
