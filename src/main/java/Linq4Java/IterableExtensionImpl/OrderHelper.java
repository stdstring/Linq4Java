package Linq4Java.IterableExtensionImpl;

import Linq4Java.IterableExtensionHelper.EnumerationAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author std_string
 */
final class OrderHelper {
    public static <TSource, TKey> Iterable<Iterable<TSource>> order(Iterable<Iterable<TSource>> source, Comparator<TSource> comparer) {
        List<Iterable<TSource>> groupList = new ArrayList<Iterable<TSource>>();
        for(Iterable<TSource> sourceIterable : source) {
            groupList.addAll(orderPortion(sourceIterable, comparer));
        }
        return groupList;
    }
    
    private static <TSource> Collection<Iterable<TSource>> orderPortion(Iterable<TSource> source, Comparator<TSource> comparer) {
        List<TSource> sourceList = Collections.list(new EnumerationAdapter<TSource>(source.iterator()));
        Collections.sort(sourceList, comparer);
        List<Iterable<TSource>> groupList = new ArrayList<Iterable<TSource>>();
        List<TSource> group = null;
        TSource groupItem = null;
        boolean firstIteration = true;
        for(TSource item : sourceList) {
            if(firstIteration || comparer.compare(groupItem, item) != 0) {
                group = new ArrayList<TSource>();
                groupList.add(group);
                groupItem = item;
                firstIteration = false;
            }
            group.add(item);
        }
        return groupList;
    }
    
    private OrderHelper() {
    }
}