package Linq4Java.Functional;

/**
 *
 * @author std_string
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
        if (!isValueCreated) {
            value = factory.func();
            isValueCreated = true;
        }
        return value;
    }

    private final Func0<T> factory;
    private T value;
    private boolean isValueCreated;
}
