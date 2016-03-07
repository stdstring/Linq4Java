/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func2;
import IterableExtensionHelper.IndexHelper;
import java.util.Iterator;

/**
 *
 * @author avu
 */
// TODO : это итератор с предвыборкой одного элемента и последующей по нему фильтрацией. может переименовать ???
class SelectiveIteratorBase<TSource> extends IteratorBase<TSource> {

    public SelectiveIteratorBase(Iterator<TSource> iterator, Func2<TSource, Integer, Boolean> predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
        indexHelper = new IndexHelper();
    }

    @Override
    protected final boolean tryGetNextValue() {
        while(iterator.hasNext()) {
            TSource item = iterator.next();
            Integer index = indexHelper.getIndex();
            indexHelper.updateIndex();
            if(predicate.func(item, index)) {
                setCurrent(item);
                return true;
            }
        }
        return false;
    }

    private final Iterator<TSource> iterator;
    private final Func2<TSource, Integer, Boolean> predicate;
    private final IndexHelper indexHelper;
}