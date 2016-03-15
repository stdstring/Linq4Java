package Linq4Java.IterableHelper;

import Linq4Java.IterableExtension.Grouping;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author std_string
 */
public final class GroupingConverter {

    public static <TKey, TItem> TKey[] toKeyArray(Iterable<Grouping<TKey, TItem>> iterable, Class<TKey> keyType) {
        List<TKey> list = new ArrayList<TKey>();
        for(Grouping<TKey, TItem> group : iterable) {
            list.add(group.getKey());
        }
        return list.toArray((TKey[])Array.newInstance(keyType, 0));
    }

    public static <TKey, TItem> TItem[] toItemArray(Iterable<Grouping<TKey, TItem>> iterable, Class<TItem> itemType) {
        List<TItem> list = new ArrayList<TItem>();
        for(Grouping<TKey, TItem> group : iterable) {
            for(TItem item : group) {
                list.add(item);
            }
        }
        return list.toArray((TItem[])Array.newInstance(itemType, 0));
    }

    private GroupingConverter() {
    }
}
