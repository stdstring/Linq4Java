package Linq4Java.Functional;

/**
 *
 * @author std_string
 */
public final class HashCodeHelper {

    public static int hashCode(Object ... args) {
        int result = 0;
        for(Object arg : args) {
            result = (result ^ magicNumber) + (arg == null ? new Integer(0).hashCode() : arg.hashCode());
        }
        return result;
    }

    public static final int magicNumber = 13;

    private HashCodeHelper() {
    }
}
