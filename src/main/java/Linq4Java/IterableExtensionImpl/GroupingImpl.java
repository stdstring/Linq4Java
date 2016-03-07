/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import IterableExtension.Grouping;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author A.Ushakov
 */
final class GroupingImpl<TKey, TElement> implements Grouping<TKey, TElement> {

    public GroupingImpl(TKey key, Collection<TElement> collection) {
        this.key = key;
        this.collection = collection;
    }

    @Override
    public TKey getKey() {
        return key;
    }

    @Override
    public Iterator<TElement> iterator() {
        return collection.iterator();
    }
    
    Collection<TElement> getGroup() {
        return collection;
    }

    private final TKey key;
    private final Collection<TElement> collection;
}
