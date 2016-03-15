package Linq4Java.Functional;

/**
 *
 * @author std_string
 */
public class Tuple2<T1, T2> {
    
    public Tuple2(T1 item1, T2 item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return this.getClass().equals(obj.getClass()) ? equals((Tuple2<T1, T2>)obj) : false;
    }

    public boolean equals(Tuple2<T1, T2> obj) {
        if(obj == null) return false;
        return item1.equals(obj.item1) && item2.equals(obj.item2);
    }

    @Override
    public int hashCode() {
        return HashCodeHelper.hashCode(item1, item2);
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    private final T1 item1;
    private final T2 item2;
}