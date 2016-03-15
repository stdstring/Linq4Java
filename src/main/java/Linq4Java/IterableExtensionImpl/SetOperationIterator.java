package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author std_string
 */
final class SetOperationIterator<TSource> extends SelectiveIteratorBase<TSource> {

    public SetOperationIterator(Iterator<TSource> iterator,
            final Iterable<TSource> other,
            final Func2<TSource, Set<TSource>, Boolean> setOperation) {
        super(iterator,
                new Func2<TSource, Integer, Boolean>() {

                    @Override
                    public Boolean func(TSource param, Integer index) {
                        return setOperation.func(param, knownObjects);
                    }
                    private final Set<TSource> knownObjects = new HashSet<TSource>();

                    {
                        for (TSource item : other) {
                            knownObjects.add(item);
                        }
                    }
                });
    }
}
