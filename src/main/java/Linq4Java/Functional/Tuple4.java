package Linq4Java.Functional;

/**
 *
 * @author std_string
 */
public class Tuple4<T1, T2, T3, T4> {

    public Tuple4(T1 item1, T2 item2, T3 item3, T4 item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        return this.getClass().equals(obj.getClass()) ? equals((Tuple4<T1, T2, T3, T4>)obj) : false;
    }

    public boolean equals(Tuple4<T1, T2, T3, T4> obj) {
        if(obj == null) return false;
        return item1.equals(obj.item1) && item2.equals(obj.item2) && item3.equals(obj.item3) && item4.equals(obj.item4);
    }

    @Override
    public int hashCode() {
        return HashCodeHelper.hashCode(item1, item2, item3, item4);
    }

    public T1 getItem1() {
        return item1;
    }

    public T2 getItem2() {
        return item2;
    }

    public T3 getItem3() {
        return item3;
    }

    public T4 getItem4() {
        return item4;
    }

    private final T1 item1;
    private final T2 item2;
    private final T3 item3;
    private final T4 item4;
}