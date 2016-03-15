package Linq4Java.IterableHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author std_string
 */
public final class IterableConverter {
    public static <TItem> TItem[] toArray(Iterable<TItem> iterable, Class<TItem> itemType) {
        List<TItem> list = new ArrayList<TItem>();
        for(TItem item : iterable) {
            list.add(item);
        }
        return list.toArray((TItem[])Array.newInstance(itemType, 0));
    }
    
    public static <TItem> TItem[] toArray(Iterable<TItem> iterable, TItem[] itemArray) {
        List<TItem> list = new ArrayList<TItem>();
        for(TItem item : iterable) {
            list.add(item);
        }
        return list.toArray(itemArray);
    }
}
