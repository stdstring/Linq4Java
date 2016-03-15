package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class SimpleIterableImpl<TSource> implements Iterable<TSource> {

    public SimpleIterableImpl(Func0<Iterator<TSource>> iteratorFactory) {
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public Iterator<TSource> iterator() {
        return iteratorFactory.func();
    }

    private final Func0<Iterator<TSource>> iteratorFactory;
}
