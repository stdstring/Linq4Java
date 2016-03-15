package Linq4Java.IterableExtensionHelper;

/**
 *
 * @author std_string
 */
public final class IndexHelper {

    public int getIndex() {
        return index;
    }

    public void updateIndex() {
        ++index;
    }

    private int index = 0;
}