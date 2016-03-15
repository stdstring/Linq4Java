package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import Linq4Java.Functional.Func1;
import Linq4Java.Functional.Func2;
import Linq4Java.Functional.FuncsAdapter;
import Linq4Java.Functional.Tuple2;
import Linq4Java.IterableExtension.Grouping;
import Linq4Java.IterableExtension.IterableExtension;
import Linq4Java.IterableExtension.OrderedIterableExtension;
import Linq4Java.IterableExtensionHelper.ComparatorFactory;
import Linq4Java.IterableExtensionHelper.DefaultValueFactory;
import Linq4Java.IterableExtensionHelper.SetOperationFuncs;
import Linq4Java.IterableExtensionHelper.TrivialFuncs;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author std_string
 */
class IterableExtensionImpl<TSource> implements IterableExtension<TSource> {

    // aggregate
    @Override
    public TSource aggregate(Class<TSource> elementClass, Func2<TSource, TSource, TSource> accumulateFunc) {
        return aggregate(DefaultValueFactory.get(elementClass), accumulateFunc, TrivialFuncs.<TSource>getTrivialSelector());
    }

    @Override
    public <TAccumulate> TAccumulate aggregate(TAccumulate seed, Func2<TAccumulate, TSource, TAccumulate> accumulateFunc) {
        return aggregate(seed, accumulateFunc, TrivialFuncs.<TAccumulate>getTrivialSelector());
    }

    @Override
    public <TAccumulate, TResult> TResult aggregate(TAccumulate seed, Func2<TAccumulate, TSource, TAccumulate> accumulateFunc, Func1<TAccumulate, TResult> resultSelector) {
        return AggregateHelper.aggregate(iterable, seed, accumulateFunc, resultSelector);
    }

    // all
    @Override
    public boolean all(Func1<TSource, Boolean> predicate) {
        return AggregateHelper.all(iterable, predicate);
    }

    // any
    @Override
    public boolean any() {
        return AggregateHelper.any(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public boolean any(Func1<TSource, Boolean> predicate) {
        return AggregateHelper.any(iterable, predicate);
    }
    
    // average
    @Override
    public Number average(Class<TSource> elementClass) {
        return AggregateHelper.<TSource, TSource>average(iterable, TrivialFuncs.<TSource>getTrivialSelector(), elementClass);
    }

    @Override
    public <TResult extends Number> Number average(Func1<TSource, TResult> selector, Class<TResult> selectorElementClass) {
        return AggregateHelper.average(iterable, selector, selectorElementClass);
    }

    // concat
    @Override
    public IterableExtension<TSource> concat(Iterable<TSource> other) {
        Collection<Iterable<TSource>> iterables = Arrays.asList(iterable, other);
        return new IterableExtensionImpl(new ConcatIterable(iterables));
    }

    // contains
    @Override
    public boolean contains(final TSource value) {
        return AggregateHelper.any(iterable, new Func1<TSource, Boolean>(){@Override public Boolean func(TSource param){return value.equals(param);}});
    }

    // count
    @Override
    public int count() {
        return AggregateHelper.count(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public int count(Func1<TSource, Boolean> predicate) {
        return AggregateHelper.count(iterable, predicate);
    }

    // defaultIfEmpty
    @Override
    public IterableExtension<TSource> defaultIfEmpty(Class<TSource> elementClass) {
        return defaultIfEmpty(DefaultValueFactory.<TSource>get(elementClass));
    }

    @Override
    public IterableExtension<TSource> defaultIfEmpty(final TSource defaultValue) {
        Func0<Iterator<TSource>> iteratorFactory = new Func0<Iterator<TSource>>() {
            @Override
            public Iterator<TSource> func() {
                Iterator<TSource> iterator = iterable.iterator();
                return iterator.hasNext() ? iterator : Arrays.asList(defaultValue).iterator();
            }            
        };
        return new IterableExtensionImpl(new SimpleIterableImpl(iteratorFactory));
    }

    // distinct
    @Override
    public IterableExtension<TSource> distinct() {
        return new IterableExtensionImpl(new DistinctIterable(iterable));
    }

    // elementAt
    @Override
    public TSource elementAt(int index) {
        return ElementSelectorHelper.elementAt(iterable, index);
    }

    // elementAtOrDefault
    @Override
    public TSource elementAtOrDefault(Class<TSource> elementClass, int index) {
        return ElementSelectorHelper.elementAtOrDefault(iterable, index, DefaultValueFactory.get(elementClass));
    }

    // except
    @Override
    public IterableExtension<TSource> except(Iterable<TSource> other) {
        return new IterableExtensionImpl(new SetOperationIterable(iterable, other, SetOperationFuncs.getExceptOp()));
    }

    // first
    @Override
    public TSource first() {
        return ElementSelectorHelper.first(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public TSource first(Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.first(iterable, predicate);
    }

    // firstOrDefault
    @Override
    public TSource firstOrDefault(Class<TSource> elementClass) {
        return ElementSelectorHelper.firstOrDefault(iterable, TrivialFuncs.<TSource>getTrivialPredicate(), DefaultValueFactory.get(elementClass));
    }

    @Override
    public TSource firstOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.firstOrDefault(iterable, predicate, DefaultValueFactory.get(elementClass));
    }

    @Override
    public <TKey> IterableExtension<Grouping<TKey, TSource>> groupBy(Func1<TSource, TKey> keySelector) {
        Iterable<Grouping<TKey, TSource>> result = GroupHelper.createGroupIterable(iterable, keySelector, TrivialFuncs.<TSource>getTrivialSelector());
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TKey, TResult> IterableExtension<Grouping<TKey, TResult>> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TResult> elementSelector) {
        Iterable<Grouping<TKey, TResult>> result = GroupHelper.createGroupIterable(iterable, keySelector, elementSelector);
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TKey, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func2<TKey, Iterable<TSource>, TResult> resultSelector) {
        Iterable<TResult> result = new TransformIterable(GroupHelper.createGroupIterable(iterable, keySelector, TrivialFuncs.<TSource>getTrivialSelector()), FuncsHelper.groupResultSelectorAdapter(resultSelector));
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TKey, TElement, TResult> IterableExtension<TResult> groupBy(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector, Func2<TKey, Iterable<TElement>, TResult> resultSelector) {
        Iterable<TResult> result = new TransformIterable(GroupHelper.createGroupIterable(iterable, keySelector, elementSelector), FuncsHelper.groupResultSelectorAdapter(resultSelector));
        return new IterableExtensionImpl(result);
    }

    // groupJoin
    @Override
    public <TInner, TKey, TResult> IterableExtension<TResult> groupJoin(final Iterable<TInner> inner, final Func1<TSource, TKey> outerKeySelector, final Func1<TInner, TKey> innerKeySelector, Func2<TSource, Iterable<TInner>, TResult> resultSelector) {
        Func0<Iterator<Tuple2<TSource, Iterable<TInner>>>> factory = new Func0<Iterator<Tuple2<TSource, Iterable<TInner>>>>() {
            @Override
            public Iterator<Tuple2<TSource, Iterable<TInner>>> func() {
                Iterable<Grouping<TKey, TSource>> outerSource = GroupHelper.createGroupIterable(iterable, outerKeySelector, TrivialFuncs.<TSource>getTrivialSelector());
                Map<TKey, Grouping<TKey, TInner>> innerSource = GroupHelper.createGroupMap(inner, innerKeySelector, TrivialFuncs.<TInner>getTrivialSelector());
                return new GroupJoinIterator(outerSource.iterator(), innerSource);
            }
        };
        Iterable<TResult> result = new TransformIterable(new SimpleIterableImpl(factory), FuncsHelper.disjunctiveAdapter(resultSelector));
        return new IterableExtensionImpl(result);
    }

    // intersect
    @Override
    public IterableExtension<TSource> intersect(Iterable<TSource> other) {
        return new IterableExtensionImpl(new SetOperationIterable(iterable, other, SetOperationFuncs.getIntersectOp()));
    }

    // join
    @Override
    public <TInner, TKey, TResult> IterableExtension<TResult> join(final Iterable<TInner> inner, final Func1<TSource, TKey> outerKeySelector, final Func1<TInner, TKey> innerKeySelector, Func2<TSource, TInner, TResult> resultSelector) {
        Func0<Iterator<Tuple2<TSource, TInner>>> factory = new Func0<Iterator<Tuple2<TSource, TInner>>>() {
            @Override
            public Iterator<Tuple2<TSource, TInner>> func() {
                Iterable<Grouping<TKey, TSource>> outerSource = GroupHelper.createGroupIterable(iterable, outerKeySelector, TrivialFuncs.<TSource>getTrivialSelector());
                Map<TKey, Grouping<TKey, TInner>> innerSource = GroupHelper.createGroupMap(inner, innerKeySelector, TrivialFuncs.<TInner>getTrivialSelector());
                return new JoinIterator(outerSource.iterator(), innerSource);
            }
        };
        Iterable<TResult> result = new TransformIterable(new SimpleIterableImpl(factory), FuncsHelper.disjunctiveAdapter(resultSelector));
        return new IterableExtensionImpl(result);
    }

    // last
    @Override
    public TSource last() {
        return ElementSelectorHelper.last(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public TSource last(Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.last(iterable, predicate);
    }

    // lastOrDefault
    @Override
    public TSource lastOrDefault(Class<TSource> elementClass) {
        return ElementSelectorHelper.lastOrDefault(iterable, TrivialFuncs.<TSource>getTrivialPredicate(), DefaultValueFactory.get(elementClass));
    }

    @Override
    public TSource lastOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.lastOrDefault(iterable, predicate, DefaultValueFactory.get(elementClass));
    }

    // longCount
    @Override
    public long longCount() {
        return AggregateHelper.longCount(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public long longCount(Func1<TSource, Boolean> predicate) {
        return AggregateHelper.longCount(iterable, predicate);
    }
    
    // max
    @Override
    public TSource max() {
        return ElementSelectorHelper.max(iterable);
    }

    @Override
    public TSource max(Comparator<TSource> comparator) {
        return ElementSelectorHelper.max(iterable, TrivialFuncs.<TSource>getTrivialSelector(), comparator);
    }

    @Override
    public <TResult extends Comparable<TResult>> TResult max(Func1<TSource, TResult> selector) {
        return ElementSelectorHelper.max(iterable, selector, ComparatorFactory.<TResult>create(true));
    }

    // min
    @Override
    public TSource min() {
        return ElementSelectorHelper.min(iterable);
    }

    @Override
    public TSource min(Comparator<TSource> comparator) {
        return ElementSelectorHelper.min(iterable, TrivialFuncs.<TSource>getTrivialSelector(), comparator);
    }

    @Override
    public <TResult extends Comparable<TResult>> TResult min(Func1<TSource, TResult> selector) {
        return ElementSelectorHelper.min(iterable, selector, ComparatorFactory.<TResult>create(true));
    }

    // orderBy
    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> orderBy(Func1<TSource, TKey> keySelector) {
        return new OrderedIterableExtensionImpl(OrderedIterableImpl.createOrderedIterable(iterable, keySelector, ComparatorFactory.<TKey>create(true)));
    }

    // orderBy
    @Override
    public <TKey> OrderedIterableExtension<TSource> orderBy(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return new OrderedIterableExtensionImpl(OrderedIterableImpl.createOrderedIterable(iterable, keySelector, comparer));
    }

    // orderByDescending
    @Override
    public <TKey extends Comparable<TKey>> OrderedIterableExtension<TSource> orderByDescending(Func1<TSource, TKey> keySelector) {
        return new OrderedIterableExtensionImpl(OrderedIterableImpl.createOrderedIterable(iterable, keySelector, ComparatorFactory.<TKey>create(false)));
    }

    // orderByDescending
    @Override
    public <TKey> OrderedIterableExtension<TSource> orderByDescending(Func1<TSource, TKey> keySelector, Comparator<TKey> comparer) {
        return new OrderedIterableExtensionImpl(OrderedIterableImpl.createOrderedIterable(iterable, keySelector, ComparatorFactory.createDescending(comparer)));
    }

    // reverse
    @Override
    public IterableExtension<TSource> reverse() {
        return new IterableExtensionImpl(ReverseHelper.createReverseIterable(iterable));
    }

    // select
    @Override
    public <TResult> IterableExtension<TResult> select(Func1<TSource, TResult> selector) {
        Iterable<TResult> result = new TransformIterable(iterable, FuncsAdapter.<TSource, Integer, TResult>fakeParam2(selector));
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TResult> IterableExtension<TResult> select(Func2<TSource, Integer, TResult> selector) {
        return new IterableExtensionImpl(new TransformIterable(iterable, selector));
    }

    // selectMany
    @Override
    public <TResult> IterableExtension<TResult> selectMany(Func1<TSource, Iterable<TResult>> selector) {
        Iterable<TResult> result = selectMany(FuncsAdapter.<TSource, Integer, Iterable<TResult>>fakeParam2(selector));
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TResult> IterableExtension<TResult> selectMany(Func2<TSource, Integer, Iterable<TResult>> selector) {
        Iterable<TResult> result = new ConcatIterable(new TransformIterable<TSource, Iterable<TResult>>(iterable, selector));
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TCollection, TResult> IterableExtension<TResult> selectMany(Func1<TSource, Iterable<TCollection>> collectionSelector, Func2<TSource, TCollection, TResult> resultSelector) {
        Iterable<TResult> result = selectMany(FuncsAdapter.<TSource, Integer, Iterable<TCollection>>fakeParam2(collectionSelector), resultSelector);
        return new IterableExtensionImpl(result);
    }

    @Override
    public <TCollection, TResult> IterableExtension<TResult> selectMany(Func2<TSource, Integer, Iterable<TCollection>> collectionSelector, Func2<TSource, TCollection, TResult> resultSelector) {
        Iterable<Tuple2<TSource, TCollection>> intermediateIterable = selectMany(FuncsHelper.unitingAdapter(collectionSelector));
        Iterable<TResult> result = new TransformIterable(intermediateIterable, FuncsHelper.disjunctiveAdapter(resultSelector));
        return new IterableExtensionImpl(result);
    }

    // sequenceEqual
    @Override
    public boolean sequenceEqual(Iterable<TSource> other) {
        return AggregateHelper.equals(iterable, other);
    }

    // single
    @Override
    public TSource single() {
        return ElementSelectorHelper.single(iterable, TrivialFuncs.<TSource>getTrivialPredicate());
    }

    @Override
    public TSource single(Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.single(iterable, predicate);
    }

    // singleOrDefault
    @Override
    public TSource singleOrDefault(Class<TSource> elementClass) {
        return ElementSelectorHelper.singleOrDefault(iterable, TrivialFuncs.<TSource>getTrivialPredicate(), DefaultValueFactory.get(elementClass));
    }

    @Override
    public TSource singleOrDefault(Class<TSource> elementClass, Func1<TSource, Boolean> predicate) {
        return ElementSelectorHelper.singleOrDefault(iterable, predicate, DefaultValueFactory.get(elementClass));
    }

    // skip
    @Override
    public IterableExtension<TSource> skip(final int count) {
        final Func2<TSource, Integer, Boolean> predicate = new Func2<TSource, Integer, Boolean>() {
            @Override
            public Boolean func(TSource param, Integer index) {
                return index < count;
            }
        };
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new SkipIterator(iterable.iterator(), predicate); }});
        return new IterableExtensionImpl(result);
    }

    // skipWhile
    @Override
    public IterableExtension<TSource> skipWhile(final Func1<TSource, Boolean> predicate) {
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new SkipIterator(iterable.iterator(), FuncsAdapter.<TSource, Integer, Boolean>fakeParam2(predicate)); }});
        return new IterableExtensionImpl(result);
    }

    @Override
    public IterableExtension<TSource> skipWhile(final Func2<TSource, Integer, Boolean> predicate) {
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new SkipIterator(iterable.iterator(), predicate); }});
        return new IterableExtensionImpl(result);
    }

    // sum
    @Override
    public Number sum(Class<TSource> elementClass) {
        return AggregateHelper.sum(iterable, TrivialFuncs.<TSource>getTrivialSelector(), elementClass);
    }

    @Override
    public <TResult extends Number> Number sum(Func1<TSource, TResult> selector, Class<TResult> selectorElementClass) {
        return AggregateHelper.sum(iterable, selector, selectorElementClass);
    }

    // take
    @Override
    public IterableExtension<TSource> take(final int count) {
        final Func2<TSource, Integer, Boolean> predicate = new Func2<TSource, Integer, Boolean>() {
            @Override
            public Boolean func(TSource param, Integer index) {
                return index < count;
            }
        };
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new TakeIterator(iterable.iterator(), predicate); }});
        return new IterableExtensionImpl(result);
    }

    // takeWhile
    @Override
    public IterableExtension<TSource> takeWhile(final Func1<TSource, Boolean> predicate) {
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new TakeIterator(iterable.iterator(), FuncsAdapter.<TSource, Integer, Boolean>fakeParam2(predicate)); }});
        return new IterableExtensionImpl(result);
    }

    @Override
    public IterableExtension<TSource> takeWhile(final Func2<TSource, Integer, Boolean> predicate) {
        Iterable<TSource> result = new SimpleIterableImpl(new Func0<Iterator<TSource>>() {@Override public Iterator<TSource> func() { return new TakeIterator(iterable.iterator(), predicate); }});
        return new IterableExtensionImpl(result);
    }

    // toArray
    @Override
    public TSource[] toArray(Class<TSource> elementClass) {
        return ConvertHelper.toArray(iterable, elementClass);
    }

    @Override
    public List<TSource> toList() {
        return ConvertHelper.toList(iterable);
    }

    @Override
    public <TKey> Map<TKey, TSource> toMap(Func1<TSource, TKey> keySelector) {
        return ConvertHelper.toMap(iterable, keySelector);
    }

    @Override
    public <TKey, TElement> Map<TKey, TElement> toMap(Func1<TSource, TKey> keySelector, Func1<TSource, TElement> elementSelector) {
        return ConvertHelper.toMap(iterable, keySelector, elementSelector);
    }

    // union
    @Override
    public IterableExtension<TSource> union(Iterable<TSource> other) {
        Collection<Iterable<TSource>> iterables = Arrays.asList(iterable, other);
        return new IterableExtensionImpl(new DistinctIterable(new ConcatIterable(iterables)));
    }

    // where
    @Override
    public IterableExtension<TSource> where(Func1<TSource, Boolean> predicate) {
        return new IterableExtensionImpl(new ConditionIterable(iterable, FuncsAdapter.<TSource, Integer, Boolean>fakeParam2(predicate)));
    }

    @Override
    public IterableExtension<TSource> where(Func2<TSource, Integer, Boolean> predicate) {
        return new IterableExtensionImpl(new ConditionIterable(iterable, predicate));
    }
    
    @Override
    public Iterator<TSource> iterator() {
        return iterable.iterator();
    }

    IterableExtensionImpl(Iterable<TSource> iterable){
        this.iterable = iterable;
    }

    private final Iterable<TSource> iterable;
}