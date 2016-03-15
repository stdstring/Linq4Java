package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import Linq4Java.Functional.Func1;
import Linq4Java.IterableExtension.Grouping;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author std_string
 */
final class GroupHelper {

    public static <TSource, TKey, TResult> Iterable<Grouping<TKey, TResult>> createGroupIterable(final Iterable<TSource> sourceIterable, final Func1<TSource, TKey> keySelector, final Func1<TSource, TResult> elementSelector) {
        Func0<Iterator<Grouping<TKey, TResult>>> factory = new Func0<Iterator<Grouping<TKey, TResult>>>() {

            @Override
            public Iterator<Grouping<TKey, TResult>> func() {
                return group(sourceIterable, keySelector, elementSelector).iterator();
            }
        };
        return new SimpleIterableImpl(factory);
    }

    public static <TSource, TKey, TResult> Map<TKey, Grouping<TKey, TResult>> createGroupMap(final Iterable<TSource> sourceIterable, final Func1<TSource, TKey> keySelector, final Func1<TSource, TResult> elementSelector) {
        return groupMap(sourceIterable, keySelector, elementSelector);
    }
    
    private static <TSource, TKey, TResult> Iterable<Grouping<TKey, TResult>> group(Iterable<TSource> source, Func1<TSource, TKey> keySelector, Func1<TSource, TResult> elementSelector) {
        Map<TKey, Collection<TResult>> keyMap = new HashMap<TKey, Collection<TResult>>();
        List<Grouping<TKey, TResult>> dest = new ArrayList<Grouping<TKey, TResult>>();
        for(TSource item : source) {
            TKey key = keySelector.func(item);
            TResult result = elementSelector.func(item);
            Collection<TResult> group;
            if(keyMap.containsKey(key)) {
                group = keyMap.get(key);
            }
            else {
                GroupingImpl<TKey, TResult> groupImpl = new GroupingImpl<TKey, TResult>(key, group = new ArrayList<TResult>());
                dest.add(groupImpl);
                keyMap.put(key, group);
            }
            group.add(result);
        }
        return dest;
    }
    
    private static <TSource, TKey, TResult> Map<TKey, Grouping<TKey, TResult>> groupMap(Iterable<TSource> source, Func1<TSource, TKey> keySelector, Func1<TSource, TResult> elementSelector) {
        Map<TKey, Collection<TResult>> keyMap = new HashMap<TKey, Collection<TResult>>();
        Map<TKey, Grouping<TKey, TResult>> dest = new HashMap<TKey, Grouping<TKey, TResult>>();
        for(TSource item : source) {
            TKey key = keySelector.func(item);
            TResult result = elementSelector.func(item);
            Collection<TResult> group;
            if(keyMap.containsKey(key)) {
                group = keyMap.get(key);
            }
            else {
                GroupingImpl<TKey, TResult> groupImpl = new GroupingImpl<TKey, TResult>(key, group = new ArrayList<TResult>());
                dest.put(key, groupImpl);
                keyMap.put(key, group);
            }
            group.add(result);
        }
        return dest;
    }

    private GroupHelper() {
    }
}