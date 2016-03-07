/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func0;
import java.util.Iterator;

/**
 *
 * @author avu
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
