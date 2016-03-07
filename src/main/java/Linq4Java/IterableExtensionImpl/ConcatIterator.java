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

final class ConcatIterator<TSource> implements Iterator<TSource> {

    public ConcatIterator(Iterator<Iterable<TSource>> iterators) {
        this.iterators = iterators;
        currentIterator = null;
    }

    @Override
    public boolean hasNext() {
        // ищем первый итератор (сначало текущий, потом в наборе), у которого есть элементы
        // если мы достигнем конца набора итераторов, то такого итератора нет и значит следующего элемента нет
        while(currentIterator == null || !currentIterator.hasNext()) {
            if(iterators.hasNext()) {
                currentIterator = iterators.next().iterator();
            }
            else {
                break;
            }
        }
        return currentIterator != null && currentIterator.hasNext();
    }

    @Override
    public TSource next() {
        // ищем первый итератор (сначало текущий, потом в наборе), у которого есть элементы
        // если мы достигнем конца набора итераторов, то упадем с исключением NoSuchElementException, как и должно быть
        while(currentIterator == null || !currentIterator.hasNext()) {
            currentIterator = iterators.next().iterator();
        }
        return currentIterator.next();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    private final Iterator<Iterable<TSource>> iterators;
    private Iterator<TSource> currentIterator;
}
