package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func2;
import Linq4Java.IterableExtensionHelper.IndexHelper;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class SkipIterator<TSource> extends IteratorBase<TSource> {

    public SkipIterator(Iterator<TSource> iterator, Func2<TSource, Integer, Boolean> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        beforeSkip = true;
    }

    @Override
    protected final boolean tryGetNextValue() {
        if(!iterator.hasNext()) return false;
        if(beforeSkip) {
            Boolean result = skipWhile();
            beforeSkip = false;
            return result;
        }
        setCurrent(iterator.next());
        return true;
    }

    private boolean skipWhile() {
        IndexHelper indexHelper = new IndexHelper();
        while(iterator.hasNext()) {
            TSource item = iterator.next();
            if(!predicate.func(item, indexHelper.getIndex())) {
                setCurrent(item);
                return true;
            }
            indexHelper.updateIndex();
        }
        return false;
    }

    private final Iterator<TSource> iterator;
    private final Func2<TSource, Integer, Boolean> predicate;
    private Boolean beforeSkip;
}
