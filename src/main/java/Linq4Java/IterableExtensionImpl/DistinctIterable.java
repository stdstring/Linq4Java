/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import java.util.Iterator;

/**
 *
 * @author avu
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
