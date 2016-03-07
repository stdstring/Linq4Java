/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author aushakov
 */

// TODO : это итератор с предвыборкой одного элемента. может переименовать ???
abstract class IteratorBase<TSource> implements Iterator<TSource> {

    public IteratorBase() {
        current = null;
        setState(IteratorState.UNKNOWN);
    }

    @Override
    public final boolean hasNext() {
        if(getState().equals(IteratorState.ACTIVE)) return true;
        if(getState().equals(IteratorState.FINISHED)) return false;
        // пытаемся найти следующий (удовлетворяющий критериям) элемент
        boolean hasNext = tryGetNextValue();
        setState(hasNext ? IteratorState.ACTIVE : IteratorState.FINISHED);
        return hasNext;
    }

    @Override
    public final TSource next() {
        if(getState().equals(IteratorState.UNKNOWN))
            hasNext();
        if(getState().equals(IteratorState.ACTIVE)) {
            setState(IteratorState.UNKNOWN);
            return current;
        }
        throw new NoSuchElementException();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    abstract protected boolean tryGetNextValue();

    protected final TSource getCurrent() {
        return current;
    }

    protected final void setCurrent(TSource newValue) {
        current = newValue;
    }

    protected final IteratorState getState() {
        return state;
    }

    protected final void setState(IteratorState newValue) {
        state = newValue;
    }

    private IteratorState state;
    private TSource current;
}
