/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import Functional.Func2;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author avu
 */
final class DistinctIterator<TSource> extends SelectiveIteratorBase<TSource> {

    public DistinctIterator(Iterator<TSource> iterator) {
        super(iterator,
                new Func2<TSource, Integer, Boolean>() {
                    @Override
                    public Boolean func(TSource param, Integer index) {
                        Boolean notContains = !knownObjects.contains(param);
                        if (notContains) {
                            knownObjects.add(param);
                        }
                        return notContains;
                    }
                    private Set<TSource> knownObjects = new HashSet<TSource>();
                });
    }
}
