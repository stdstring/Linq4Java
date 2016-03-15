package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import Linq4Java.IterableExtensionHelper.IndexHelper;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class TakeIterator<TSource> extends IteratorBase<TSource> {

    public TakeIterator(Iterator<TSource> iterator, Func2<TSource, Integer, Boolean> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        indexHelper = new IndexHelper();
    }

    @Override
    protected final boolean tryGetNextValue() {
        if(!iterator.hasNext() || predicateFailOccur) return false;
        TSource item = iterator.next();
        if(predicate.func(item, indexHelper.getIndex())) {
            setCurrent(item);
            indexHelper.updateIndex();
        }
        else {
            predicateFailOccur = true;
        }
        return !predicateFailOccur;
    }

    private final Iterator<TSource> iterator;
    private final Func2<TSource, Integer, Boolean> predicate;
    private final IndexHelper indexHelper;
    private boolean predicateFailOccur = false;
}
