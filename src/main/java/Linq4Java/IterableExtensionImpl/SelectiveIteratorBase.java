package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import Linq4Java.IterableExtensionHelper.IndexHelper;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
class SelectiveIteratorBase<TSource> extends IteratorBase<TSource> {

    public SelectiveIteratorBase(Iterator<TSource> iterator, Func2<TSource, Integer, Boolean> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        indexHelper = new IndexHelper();
    }

    @Override
    protected final boolean tryGetNextValue() {
        while(iterator.hasNext()) {
            TSource item = iterator.next();
            Integer index = indexHelper.getIndex();
            indexHelper.updateIndex();
            if(predicate.func(item, index)) {
                setCurrent(item);
                return true;
            }
        }
        return false;
    }

    private final Iterator<TSource> iterator;
    private final Func2<TSource, Integer, Boolean> predicate;
    private final IndexHelper indexHelper;
}