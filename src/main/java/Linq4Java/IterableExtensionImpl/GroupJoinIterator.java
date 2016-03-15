package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Tuple2;
import Linq4Java.IterableExtension.Grouping;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author std_string
 */
final class GroupJoinIterator<TSource, TInner, TKey> extends IteratorBase<Tuple2<TSource, Iterable<TInner>>> {

    public GroupJoinIterator(Iterator<Grouping<TKey, TSource>> outer, Map<TKey, Grouping<TKey, TInner>> inner) {
        this.outer = outer;
        outerGroup = null;
        this.inner = inner;
    }

    @Override
    protected final boolean tryGetNextValue() {
        // current outer group is null (initial case)
        if(outerGroup == null) {
            // try get outerGrouping, but not successful
            if(!searchOuterGroup()) return false;
        }
        // current outer group is finished
        if(!outerGroup.getItem2().hasNext()) {
            // try get outerGrouping, but not successful
            if(!searchOuterGroup()) return false;
        }
        // all ok
        TSource outerItem = outerGroup.getItem2().next();
        Iterable<TInner> innerGroup = inner.get(outerGroup.getItem1());
        if(innerGroup == null) innerGroup = new ArrayList<TInner>();
        setCurrent(new Tuple2<TSource, Iterable<TInner>>(outerItem, innerGroup));
        return true;
    }

    private boolean searchOuterGroup() {
        while(outer.hasNext()) {
            Grouping<TKey, TSource> group = outer.next();
            outerGroup = new Tuple2<TKey, Iterator<TSource>>(group.getKey(), group.iterator());
            return true;
        }
        return false;
    }

    private final Iterator<Grouping<TKey, TSource>> outer;
    private Tuple2<TKey, Iterator<TSource>> outerGroup;
    private final Map<TKey, Grouping<TKey, TInner>> inner;
}
