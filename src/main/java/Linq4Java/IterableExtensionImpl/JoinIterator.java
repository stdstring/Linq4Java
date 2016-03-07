/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Tuple2;
import IterableExtension.Grouping;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author A.Ushakov
 */
final class JoinIterator<TSource, TInner, TKey> extends IteratorBase<Tuple2<TSource, TInner>> {

    public JoinIterator(Iterator<Grouping<TKey, TSource>> outer, Map<TKey, Grouping<TKey, TInner>> inner) {
        this.outer = outer;
        outerGroup = null;
        currentOuterGroupValue = null;
        this.inner = inner;
        innerGroup = null;
    }

    @Override
    protected boolean tryGetNextValue() {
        // current outer group is null (initial case)
        if(outerGroup == null) {
            // try get outerGrouping, but not successful
            if(!searchOuterInnerGroup()) return false;
            currentOuterGroupValue = outerGroup.getItem2().next();
        }
        // current outer group is finished
        if(!outerGroup.getItem2().hasNext() && !innerGroup.hasNext()) {
            // try get outerGrouping, but not successful
            if(!searchOuterInnerGroup()) return false;
            currentOuterGroupValue = outerGroup.getItem2().next();
        }
        // current inner group  is finished
        if(outerGroup.getItem2().hasNext() && !innerGroup.hasNext()) {
            innerGroup = inner.get(outerGroup.getItem1()).iterator();
            currentOuterGroupValue = outerGroup.getItem2().next();
        }
        // all ok
        TInner innerItem = innerGroup.next();
        setCurrent(new Tuple2<TSource, TInner>(currentOuterGroupValue, innerItem));
        return true;
    }

    private boolean searchOuterInnerGroup() {
        while(outer.hasNext()) {
            Grouping<TKey, TSource> grouping = outer.next();
            if(inner.containsKey(grouping.getKey())) {
                outerGroup = new Tuple2<TKey, Iterator<TSource>>(grouping.getKey(), grouping.iterator());
                innerGroup = inner.get(grouping.getKey()).iterator();
                return true;
            }
        }
        return false;
    }

    private final Iterator<Grouping<TKey, TSource>> outer;
    private Tuple2<TKey, Iterator<TSource>> outerGroup;
    private TSource currentOuterGroupValue;
    private final Map<TKey, Grouping<TKey, TInner>> inner;
    private Iterator<TInner> innerGroup;
}
