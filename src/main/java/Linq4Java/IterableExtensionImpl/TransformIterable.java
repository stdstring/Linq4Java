package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class TransformIterable<TSource, TResult> implements Iterable<TResult> {

    public TransformIterable(Iterable<TSource> source, Func2<TSource, Integer, TResult> selector) {
        this.selector = selector;
        this.source = source;
    }

    @Override
    public Iterator<TResult> iterator() {
        return new TransformIterator(source.iterator(), selector);
    }

    private final Func2<TSource, Integer, TResult> selector;
    private final Iterable<TSource> source;
}
