package Linq4Java.IterableExtensionImpl;

import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class ConcatIterator<TSource> implements Iterator<TSource> {

    public ConcatIterator(Iterator<Iterable<TSource>> iterators) {
        this.iterators = iterators;
        currentIterator = null;
    }

    @Override
    public boolean hasNext() {
        while(currentIterator == null || !currentIterator.hasNext()) {
            if(iterators.hasNext()) {
                currentIterator = iterators.next().iterator();
            }
            else {
                break;
            }
        }
        return currentIterator != null && currentIterator.hasNext();
    }

    @Override
    public TSource next() {
        while(currentIterator == null || !currentIterator.hasNext()) {
            currentIterator = iterators.next().iterator();
        }
        return currentIterator.next();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Iterable<TSource>> iterators;
    private Iterator<TSource> currentIterator;
}
