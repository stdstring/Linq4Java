package Linq4Java.IterableExtensionImpl;

import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class DistinctIterable<TSource> implements Iterable<TSource> {

    public DistinctIterable(Iterable<TSource> source){
        this.source = source;
    }

    @Override
    public Iterator<TSource> iterator() {
        return new DistinctIterator(source.iterator());
    }

    private final Iterable<TSource> source;
}
