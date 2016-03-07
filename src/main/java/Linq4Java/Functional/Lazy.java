/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Functional;

/**
 *
 * @author A.Ushakov
 */
public final class Lazy<T> {

    public Lazy(Func0<T> factory) {
        this.factory = factory;
        isValueCreated = false;
        value = null;
    }

    public boolean IsValueCreated() {
        return isValueCreated;
    }

    public T getValue() {
        return isValueCreated ? value : (value = factory.func());
    }

    private final Func0<T> factory;
    private T value;
    private boolean isValueCreated;
}
