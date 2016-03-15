package Linq4Java.Functional;

/**
 *
 * @author std_string
 */
public class Tuple1<T1> {

    public Tuple1(T1 item1) {
        this.item1 = item1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return this.getClass().equals(obj.getClass()) ? equals((Tuple1<T1>)obj) : false;
    }

    public boolean equals(Tuple1<T1> obj) {
        if(obj == null) return false;
        return item1.equals(obj.item1);
    }

    @Override
    public int hashCode() {
        return HashCodeHelper.hashCode(item1);
    }

    public T1 getItem1() {
        return item1;
    }

    private final T1 item1;
}
