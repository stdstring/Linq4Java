package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import Linq4Java.IterableExtensionHelper.IndexHelper;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class TransformIterator<TSource, TResult> implements Iterator<TResult> {

    public TransformIterator(Iterator<TSource> iterator, Func2<TSource, Integer, TResult> selector) {
        this.selector = selector;
        this.iterator = iterator;
        indexHelper = new IndexHelper();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public TResult next() {
        TResult value = selector.func(iterator.next(), indexHelper.getIndex());
        indexHelper.updateIndex();
        return value;
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Func2<TSource, Integer, TResult> selector;
    private final Iterator<TSource> iterator;
    private final IndexHelper indexHelper;
}
