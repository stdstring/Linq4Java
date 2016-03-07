/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import Functional.Func1;
import Functional.Tuple2;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author A.Ushakov
 */
public class ElementSelectorHelper {
    
    public static <TSource> TSource elementAt(Iterable<TSource> source, int index) {
        Tuple2<Boolean, TSource> result = (source instanceof List) ? getElementAt((List<TSource>)source, index) : getElementAt(source, index);
        if(result.getItem1())
            return result.getItem2();
        else
            throw new IndexOutOfBoundsException();
    }
    
    public static <TSource> TSource elementAtOrDefault(Iterable<TSource> source, int index, TSource defaultValue) {
        Tuple2<Boolean, TSource> result = (source instanceof List) ? getElementAt((List<TSource>)source, index) : getElementAt(source, index);
        return result.getItem1() ? result.getItem2() : defaultValue;
    }
    
    public static <TSource> TSource first(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
       Tuple2<Boolean, TSource> result = getFirst(source, predicate);
       if(result.getItem1())
            return result.getItem2();
        else
            throw new IllegalArgumentException();
    }
    
    public static <TSource> TSource firstOrDefault(Iterable<TSource> source, Func1<TSource, Boolean> predicate, TSource defaultValue) {
        Tuple2<Boolean, TSource> result = getFirst(source, predicate);
        return result.getItem1() ? result.getItem2() : defaultValue;
    }
    
    public static <TSource> TSource last(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
       Tuple2<Boolean, TSource> result = (source instanceof List) ? getLast((List<TSource>)source, predicate) : getLast(source, predicate);
       if(result.getItem1())
           return result.getItem2();
       else
           throw new IllegalArgumentException();
    }
    
    public static <TSource> TSource lastOrDefault(Iterable<TSource> source, Func1<TSource, Boolean> predicate, TSource defaultValue) {
        Tuple2<Boolean, TSource> result = (source instanceof List) ? getLast((List<TSource>)source, predicate) : getLast(source, predicate);
        return result.getItem1() ? result.getItem2() : defaultValue;
    }
    
    public static <TSource> TSource max(Iterable<TSource> source) {
        TSource maxValue = null;
        for(TSource element : source) {
            if(element == null)
                continue;
            if(maxValue == null || ((Comparable<TSource>)maxValue).compareTo(element) < 0)
                maxValue = element;
        }
        return maxValue;
    }

    public static <TSource, TResult> TResult max(Iterable<TSource> source, Func1<TSource, TResult> selector, Comparator<TResult> comparator) {
        TResult maxValue = null;
        for(TSource element : source) {
            TResult current = selector.func(element);
            if(current == null)
                continue;
            if(maxValue == null || comparator.compare(maxValue, current) < 0)
                maxValue = current;
        }
        return maxValue;
    }
    
    public static <TSource> TSource min(Iterable<TSource> source) {
        TSource minValue = null;
        for(TSource element : source) {
            if(element == null)
                continue;
            if(minValue == null || ((Comparable<TSource>)minValue).compareTo(element) > 0)
                minValue = element;
        }
        return minValue;
    }
    
    public static <TSource, TResult> TResult min(Iterable<TSource> source, Func1<TSource, TResult> selector, Comparator<TResult> comparator) {
        TResult minValue = null;
        for(TSource element : source) {
            TResult current = selector.func(element);
            if(current == null)
                continue;
            if(minValue == null || comparator.compare(minValue, current) > 0)
                minValue = current;
        }
        return minValue;
    }
    
    public static <TSource> TSource single(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
       Tuple2<Boolean, TSource> result = getSingle(source, predicate);
       if(result.getItem1())
            return result.getItem2();
        else
            throw new IllegalArgumentException();
    }
    
    public static <TSource> TSource singleOrDefault(Iterable<TSource> source, Func1<TSource, Boolean> predicate, TSource defaultValue) {
        Tuple2<Boolean, TSource> result = getSingle(source, predicate);
        return result.getItem1() ? result.getItem2() : defaultValue;
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getElementAt(Iterable<TSource> source, int index) {
        if(index < 0)
            return new Tuple2<Boolean, TSource>(false, null);

        int iteratorIndex = -1;
        TSource element = null;
        for(Iterator<TSource> iterator = source.iterator(); iterator.hasNext() && iteratorIndex != index; ++index) {
            element = iterator.next();
        }
        if(iteratorIndex != index)
            return new Tuple2<Boolean, TSource>(false, null);
        else
            return new Tuple2<Boolean, TSource>(true, element);
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getElementAt(List<TSource> source, int index) {
        if(index < 0 || index >= source.size())
            return new Tuple2<Boolean, TSource>(false, null);
        else
            return new Tuple2<Boolean, TSource>(true, source.get(index));
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getFirst(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        for(Iterator<TSource> iterator = source.iterator(); iterator.hasNext(); ) {
            TSource current = iterator.next();
            if(predicate.func(current))
                return new Tuple2<Boolean, TSource>(true, current);
        }
        return new Tuple2<Boolean, TSource>(false, null);
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getLast(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        boolean find = false;
        TSource element = null;
        for(Iterator<TSource> iterator = source.iterator(); iterator.hasNext(); ) {
            TSource current = iterator.next();
            if(predicate.func(current)) {
                find = true;
                element = current;
            }
        }
        return find ? new Tuple2<Boolean, TSource>(true, element) : new Tuple2<Boolean, TSource>(false, null);
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getLast(List<TSource> source, Func1<TSource, Boolean> predicate) {
        for(ListIterator<TSource> iterator = source.listIterator(source.size()); iterator.hasPrevious(); ) {
            TSource current = iterator.previous();
            if(predicate.func(current))
                return new Tuple2<Boolean, TSource>(true, current);
        }
        return new Tuple2<Boolean, TSource>(false, null);
    }
    
    private static <TSource> Tuple2<Boolean, TSource> getSingle(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        TSource element = null;
        boolean find = false;
        for(TSource current : source) {
            boolean predicateValue = predicate.func(current);
            if(predicateValue && find)
                throw new IllegalArgumentException();
            if(predicateValue) {
                find = true;
                element = current;
            }
        }
        return find ? new Tuple2<Boolean, TSource>(true, element) : new Tuple2<Boolean, TSource>(false, null);
    }
    
    private ElementSelectorHelper() {
    }    
}