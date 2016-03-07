package IterableExtension;


import Functional.Func1;
import Functional.Func2;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aushakov
 */
public interface IterableExtension<TSource> extends Iterable<TSource> {
    // aggregate
    TSource aggregate(Class<TSource> elementClass, Func2<TSource, TSource, TSource> accumulateFunc);
    <TAccumulate> TAccumulate aggregate(TAccumulate seed, Func2<TAccumulate, TSource, TAccumulate> accumulateFunc);
    <TAccumulate, TResult> TResult aggregate(TAccumulate seed, Func2<TAccumulate, TSource, TAccumulate> accumulateFunc, Func1<TAccumulate, TResult> resultSelector);
    // all
    boolean all(Func1<TSource, Boolean> predicate);
    // any
    boolean any();
    boolean any(Func1<TSource, Boolean> predicate);
    // asEnumerable (???)
    // average
    Number average(Class<TSource> elementClass);
    <TResult extends Number> Number average(Func1<TSource, TResult> selector, Class<TResult> selectorElementClass);
    // concat
    IterableExtension<TSource> concat(Iterable<TSource> other);
    // contains
    boolean contains(TSource value);
    //boolean contains(TSource value, IEqualityComparer<TSource> comparer);
    // count
    int count();
    int count(Func1<TSource, Boolean> predicate);
    // defaultIfEmpty
    IterableExtension<TSource> defaultIfEmpty(Class<TSource> elementClass);
    IterableExtension<TSource> defaultIfEmpty(TSource defaultValue);
    // distinct
    IterableExtension<TSource> distinct();
    //IterableExtension<TSource> distinct(IEqualityComparer<TSource> comparer);
    // elementAt
    TSource elementAt(int index);
    // elementAtOrDefault
    TSource elementAtOrDefault(Class<TSource> elementClass, int index);
    // except
    IterableExtension<TSource> except(Iterable<TSource> other);
    //IterableExtension<TSource> except(Iterable<TSource> other, IEqualityComparer<TSource> comparer);
    // first
    TSource first();
    TSource first(Func1<TSource, Boolean> predicate);
    // firstOrDefault
    TSource firstOrDefault(Class<TSource> elementClass);
    TSource firstOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate);
    // groupBy
    <TKey> IterableExtension<Grouping<TKey, TSource>> groupBy(Func1<TSource, TKey> keySelector);
    //<TKey> IterableExtension<Grouping<TKey, TSource>> groupBy(Func1<TSource, TKey> keySelector, IEqualityComparer<TKey> comparer);
    <TKey, TResult> IterableExtension<Grouping<TKey, TResult>> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TResult> elementSelector);
    //<TKey, TResult> IterableExtension<Grouping<TKey, TResult>> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TResult> elementSelector, IEqualityComparer<TKey> comparer);
    <TKey, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func2<TKey, Iterable<TSource>, TResult> resultSelector);
    //<TKey, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func2<TKey, Iterable<TSource>, TResult> resultSelector, IEqualityComparer<TKey> comparer);
    <TKey, TElement, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector, Func2<TKey, Iterable<TElement>, TResult> resultSelector);
    //<TKey, TElement, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector, Func2<TKey, Iterable<TElement>, TResult> resultSelector, IEqualityComparer<TKey> comparer);
    // groupJoin
    <TInner, TKey, TResult> IterableExtension<TResult> groupJoin(Iterable<TInner> inner,
                                                                 Func1<TSource, TKey> outerKeySelector,
                                                                 Func1<TInner, TKey> innerKeySelector,
                                                                 Func2<TSource, Iterable<TInner>, TResult> resultSelector);
    /*<TInner, TKey, TResult> IterableExtension<TResult> groupJoin(Iterable<TInner> inner,
                                                                   Func1<TSource, TKey> outerKeySelector,
                                                                   Func1<TInner, TKey> innerKeySelector,
                                                                   Func2<TSource, Iterable<TInner>, TResult> resultSelector,
                                                                   IEqualityComparer<TKey> comparer);*/
    // intersect
    IterableExtension<TSource> intersect(Iterable<TSource> other);
    //IterableExtension<TSource> intersect(Iterable<TSource> other, IEqualityComparer<TSource> comparer);
    // join
    <TInner, TKey, TResult> IterableExtension<TResult> join(Iterable<TInner> inner,
                                                            Func1<TSource, TKey> outerKeySelector,
                                                            Func1<TInner, TKey> innerKeySelector,
                                                            Func2<TSource, TInner, TResult> resultSelector);
    /*<TInner, TKey, TResult> IterableExtension<TResult> join(Iterable<TInner> inner,
                                                              Func1<TSource, TKey> outerKeySelector,
                                                              Func1<TInner, TKey> innerKeySelector,
                                                              Func2<TSource, TInner, TResult> resultSelector,
                                                              IEqualityComparer<TKey> comparer);*/
    // last
    TSource last();
    TSource last(Func1<TSource, Boolean> predicate);
    // lastOrDefault
    TSource lastOrDefault(Class<TSource> elementClass);
    TSource lastOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate);
    // longCount
    long longCount();
    long longCount(Func1<TSource, Boolean> predicate);
    // max
    TSource max();
    TSource max(Comparator<TSource> comparator);
    <TResult extends Comparable<TResult>> TResult max(Func1<TSource, TResult> selector);
    // min
    TSource min();
    TSource min(Comparator<TSource> comparator);
    <TResult extends Comparable<TResult>> TResult min(Func1<TSource, TResult> selector);
    // orderBy
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> orderBy(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> orderBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
    // orderByDescending
    <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> orderByDescending(Func1<TSource, TKey> keySelector);
    <TKey> OrderedIterableExtension<TSource> orderByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer);
    // reverse
    IterableExtension<TSource> reverse();
    // select
    <TResult> IterableExtension<TResult> select(Func1<TSource, TResult> selector);
    <TResult> IterableExtension<TResult> select(Func2<TSource, Integer, TResult> selector);
    // selectMany
    <TResult> IterableExtension<TResult> selectMany(Func1<TSource, Iterable<TResult>> selector);
    <TResult> IterableExtension<TResult> selectMany(Func2<TSource, Integer, Iterable<TResult>> selector);
    <TCollection, TResult> IterableExtension<TResult> selectMany(Func1<TSource, Iterable<TCollection>> collectionSelector, Func2<TSource, TCollection, TResult> resultSelector);
    <TCollection, TResult> IterableExtension<TResult> selectMany(Func2<TSource, Integer, Iterable<TCollection>> collectionSelector, Func2<TSource, TCollection, TResult> resultSelector);
    // sequenceEqual
    boolean sequenceEqual(Iterable<TSource> other);
    //boolean sequenceEqual(Iterable<TSource> other, IEqualityComparer<TSource> comparer);
    // single
    TSource single();
    TSource single(Func1<TSource, Boolean> predicate);
    // singleOrDefault
    TSource singleOrDefault(Class<TSource> elementClass);
    TSource singleOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate);
    // skip
    IterableExtension<TSource> skip(int count);
    // skipWhile
    IterableExtension<TSource> skipWhile(Func1<TSource, Boolean> predicate);
    IterableExtension<TSource> skipWhile(Func2<TSource, Integer, Boolean> predicate);
    // sum
    Number sum(Class<TSource> elementClass);
    <TResult extends Number> Number sum(Func1<TSource, TResult> selector, Class<TResult> selectorElementClass);
    // take
    IterableExtension<TSource> take(int count);
    // takeWhile
    IterableExtension<TSource> takeWhile(Func1<TSource, Boolean> predicate);
    IterableExtension<TSource> takeWhile(Func2<TSource, Integer, Boolean> predicate);
    // toArray
    TSource[] toArray(Class<TSource> elementClass);
    // toList
    List<TSource> toList();
    // toLookup
    // ... toLookup(...)
    // to Map
    <TKey> Map<TKey, TSource> toMap(Func1<TSource, TKey> keySelector);
    //<TKey> Map<TKey, TSource> toMap(Func1<TSource, TKey> keySelector, IEqualityComparer<TKey> comparer);
    <TKey, TElement> Map<TKey, TElement> toMap(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector);
    //<TKey, TElement> Map<TKey, TElement> toMap(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector, IEqualityComparer<TKey> comparer);
    // union
    IterableExtension<TSource> union(Iterable<TSource> other);
    //IterableExtension<TSource> union(IterableExtension<TSource> other, IEqualityComparer<TSource> comparer);
    // where
    IterableExtension<TSource> where(Func1<TSource, Boolean> predicate);
    IterableExtension<TSource> where(Func2<TSource, Integer, Boolean> predicate);
}
