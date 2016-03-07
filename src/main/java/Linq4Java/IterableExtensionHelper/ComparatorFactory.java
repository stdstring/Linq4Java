/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionHelper;

import Functional.Func1;
import java.util.Comparator;

/**
 *
 * @author std_string
 */
public class ComparatorFactory {
    public static <TSource extends Comparable<TSource>> Comparator<TSource> create(final boolean ascending) {
        return new Comparator<TSource>() {
            @Override
            public int compare(TSource o1, TSource o2) {
                int compareSign = ascending ? 1 : -1;
                if(o1 == null && o2 == null) return 0;
                if(o1 == null && o2 != null) return -compareSign;
                if(o1 != null && o2 == null) return compareSign;
                return compareSign*o1.compareTo(o2);
            }            
        };
    }
    
    public static <TSource, TKey> Comparator<TSource> create(final Func1<TSource, TKey> keySelector, final Comparator<TKey> keyComparator) {
        return new Comparator<TSource>() {
            @Override
            public int compare(TSource o1, TSource o2) {
                if(o1 == null && o2 == null) return 0;
                if(o1 == null && o2 != null) return -1;
                if(o1 != null && o2 == null) return 1;
                return keyComparator.compare(keySelector.func(o1), keySelector.func(o2));
            }            
        };
    }
    
    public static <TSource, TKey extends Comparable<TKey>> Comparator<TSource> create(Func1<TSource, TKey> keySelector, boolean ascending) {
        return create(keySelector, ComparatorFactory.<TKey>create(ascending));
    }
    
    public static <TSource> Comparator<TSource> createDescending(final Comparator<TSource> ascendingComparator) {
        return new Comparator<TSource>() {
            @Override
            public int compare(TSource o1, TSource o2) {
                return ascendingComparator.compare(o2, o1);
            }            
        };
    }
    
    private ComparatorFactory() {}
}
