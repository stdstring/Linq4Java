/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func2;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class ConditionIterable<TSource> implements Iterable<TSource> {

    public ConditionIterable(Iterable<TSource> source, Func2<TSource, Integer, Boolean> predicate){
        this.predicate = predicate;
        this.source = source;
    }

    @Override
    public Iterator<TSource> iterator() {
        return new ConditionIterator(source.iterator(), predicate);
    }

    private final Func2<TSource, Integer, Boolean> predicate;
    private final Iterable<TSource> source;
}
