/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author A.Ushakov
 */
final class ReverseHelper {

    public static <TSource> Iterable<TSource> createReverseIterable(final Iterable<TSource> iterable) {
        Func0<Iterator<TSource>> factory = new Func0<Iterator<TSource>>() {
            @Override
            public Iterator<TSource> func() {
                return reverse(iterable).iterator();
            }
        };
        return new SimpleIterableImpl(factory);
    }

    private static <TSource> Iterable<TSource> reverse(Iterable<TSource> iterable) {
        List<TSource> list = new ArrayList<TSource>();
        for(TSource item : iterable) {
            list.add(0, item);
        }
        return list;
    }

    private ReverseHelper() {
    }
}
