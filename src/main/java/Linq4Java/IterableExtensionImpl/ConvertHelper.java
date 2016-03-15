package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func1;
import Linq4Java.IterableExtensionHelper.TrivialFuncs;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author std_string
 */
final class ConvertHelper {

    public static <TSource> TSource[] toArray(Iterable<TSource> iterable, Class<TSource> elementClass) {
        List<TSource> list = toList(iterable);
        TSource[] array = (TSource[])Array.newInstance(elementClass, list.size());
        for(int index = 0; index < list.size(); ++index) {
            array[index] = list.get(index);
        }
        return array;
    }

    public static <TSource> List<TSource> toList(Iterable<TSource> iterable) {
        List<TSource> list = new ArrayList<TSource>();
        for(TSource item : iterable){
            list.add(item);
        }
        return list;
    }

    public static <TSource, TKey> Map<TKey, TSource> toMap(Iterable<TSource> iterable, Func1<TSource, TKey> keySelector) {
        return toMap(iterable, keySelector, TrivialFuncs.<TSource>getTrivialSelector());
    }

    public static <TSource, TKey, TElement> Map<TKey, TElement> toMap(Iterable<TSource> iterable, Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector) {
        Map<TKey, TElement> map = new HashMap<TKey, TElement>();
        for(TSource item : iterable) {
            map.put(keySelector.func(item), elementSelector.func(item));
        }
        return map;
    }

    private ConvertHelper() {
    }
}
