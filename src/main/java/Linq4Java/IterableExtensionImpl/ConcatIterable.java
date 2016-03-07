/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import java.util.Iterator;

/**
 *
 * @author std_string
 */

final class ConcatIterable<TSource> implements Iterable<TSource> {

    public ConcatIterable(Iterable<Iterable<TSource>> iterables) {
        this.iterables = iterables;
    }

    @Override
    public Iterator<TSource> iterator() {        
        return new ConcatIterator(iterables.iterator());
    }

    private final Iterable<Iterable<TSource>> iterables;
}
