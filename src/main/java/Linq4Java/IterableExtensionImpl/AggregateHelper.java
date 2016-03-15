package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.EqualsHelper;
import Linq4Java.Functional.Func1;
import Linq4Java.Functional.Func2;
import Linq4Java.Functional.FuncsCastAdapter;
import Linq4Java.Functional.Tuple2;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
final class AggregateHelper {

    public static <TSource, TAccumulate, TResult> TResult aggregate(Iterable<TSource> source,
                                                                    TAccumulate seed,
                                                                    Func2<TAccumulate, TSource, TAccumulate> accumulateFunc,
                                                                    Func1<TAccumulate, TResult> resultSelector) {
        TAccumulate accumulator = seed;
        for(TSource item : source) {
            accumulator = accumulateFunc.func(accumulator, item);
        }
        return resultSelector.func(accumulator);
    }

    public static <TSource> boolean all(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        for(TSource item : source) {
            if(!predicate.func(item)) return false;
        }
        return true;
    }

    public static <TSource> boolean any(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        for(TSource item : source) {
            if(predicate.func(item)) return true;
        }
        return false;
    }

    public static <TSource> int count(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        int result = 0;
        for(TSource item : source) {
            if(predicate.func(item)) result++;
        }
        return result;
    }

    public static <TSource> long longCount(Iterable<TSource> source, Func1<TSource, Boolean> predicate) {
        long result = 0;
        for(TSource item : source) {
            if(predicate.func(item)) result++;
        }
        return result;
    }

    public static <TSource> boolean equals(Iterable<TSource> iterable1, Iterable<TSource> iterable2) {
        Iterator<TSource> iterator2 = iterable2.iterator();
        for(TSource item1 : iterable1) {
            if(!iterator2.hasNext()) return false;
            TSource item2 = iterator2.next();
            if(!EqualsHelper.equals(item1, item2)) return false;
        }
        return !iterator2.hasNext();
    }
    
    public static <TSource, TResult> Number average(Iterable<TSource> iterable, Func1<TSource, TResult> selector, Class<TResult> resultElementClass) {
        if(resultElementClass.equals(Byte.class) || resultElementClass.equals(Short.class) || resultElementClass.equals(Integer.class))
            return averageInteger(iterable, FuncsCastAdapter.<TSource, TResult, Integer>cast(selector));
        if(resultElementClass.equals(Long.class))
            return averageLong(iterable, FuncsCastAdapter.<TSource, TResult, Long>cast(selector));
        if(resultElementClass.equals(Float.class))
            return averageFloat(iterable, FuncsCastAdapter.<TSource, TResult, Float>cast(selector));
        if(resultElementClass.equals(Double.class))
            return averageDouble(iterable, FuncsCastAdapter.<TSource, TResult, Double>cast(selector));
        if(resultElementClass.equals(BigInteger.class))
            return averageBigInteger(iterable, FuncsCastAdapter.<TSource, TResult, BigInteger>cast(selector));
        if(resultElementClass.equals(BigDecimal.class))
            return averageBigDecimal(iterable, FuncsCastAdapter.<TSource, TResult, BigDecimal>cast(selector));
        throw new IllegalArgumentException();
    }
    
    public static <TSource, TResult> Number sum(Iterable<TSource> iterable, Func1<TSource, TResult> selector, Class<TResult> resultElementClass) {
        if(resultElementClass.equals(Byte.class) || resultElementClass.equals(Short.class) || resultElementClass.equals(Integer.class))
            return sumAndCountInteger(iterable, FuncsCastAdapter.<TSource, TResult, Integer>cast(selector)).getItem1();
        if(resultElementClass.equals(Long.class))
            return sumAndCountLong(iterable, FuncsCastAdapter.<TSource, TResult, Long>cast(selector)).getItem1();
        if(resultElementClass.equals(Float.class))
            return sumAndCountFloat(iterable, FuncsCastAdapter.<TSource, TResult, Float>cast(selector)).getItem1();
        if(resultElementClass.equals(Double.class))
            return sumAndCountDouble(iterable, FuncsCastAdapter.<TSource, TResult, Double>cast(selector)).getItem1();
        if(resultElementClass.equals(BigInteger.class))
            return sumAndCountBigInteger(iterable, FuncsCastAdapter.<TSource, TResult, BigInteger>cast(selector)).getItem1();
        if(resultElementClass.equals(BigDecimal.class))
            return sumAndCountBigDecimal(iterable, FuncsCastAdapter.<TSource, TResult, BigDecimal>cast(selector)).getItem1();
        throw new IllegalArgumentException();
    }
    
    private static <TSource> Double averageInteger(Iterable<TSource> iterable, Func1<TSource, Integer> selector) {
        Tuple2<Integer, Integer> sumAndCount = sumAndCountInteger(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : (double)sumAndCount.getItem1() / sumAndCount.getItem2();
    }
    
    private static <TSource> Double averageLong(Iterable<TSource> iterable, Func1<TSource, Long> selector) {
        Tuple2<Long, Integer> sumAndCount = sumAndCountLong(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : (double)sumAndCount.getItem1() / sumAndCount.getItem2();
    }
    
    private static <TSource> Float averageFloat(Iterable<TSource> iterable, Func1<TSource, Float> selector) {
        Tuple2<Float, Integer> sumAndCount = sumAndCountFloat(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : (float)sumAndCount.getItem1() / sumAndCount.getItem2();
    }
    
    private static <TSource> Double averageDouble(Iterable<TSource> iterable, Func1<TSource, Double> selector) {
        Tuple2<Double, Integer> sumAndCount = sumAndCountDouble(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : sumAndCount.getItem1() / sumAndCount.getItem2();
    }
    
    private static <TSource> BigDecimal averageBigInteger(Iterable<TSource> iterable, Func1<TSource, BigInteger> selector) {
        Tuple2<BigInteger, Integer> sumAndCount = sumAndCountBigInteger(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : new BigDecimal(sumAndCount.getItem1()).divide(new BigDecimal(sumAndCount.getItem2()));
    }
    
    private static <TSource> BigDecimal averageBigDecimal(Iterable<TSource> iterable, Func1<TSource, BigDecimal> selector) {
        Tuple2<BigDecimal, Integer> sumAndCount = sumAndCountBigDecimal(iterable, selector);
        return sumAndCount.getItem2() == 0 ? null : sumAndCount.getItem1().divide(new BigDecimal(sumAndCount.getItem2()));
    }
    
    private static <TSource> Tuple2<Integer, Integer> sumAndCountInteger(Iterable<TSource> source, Func1<TSource, Integer> selector) {
        int sum = 0;
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum += selector.func(sourceElement);
            count++;
        }
        return count == 0 ? new Tuple2<Integer, Integer>(null, count) : new Tuple2<Integer, Integer>(sum, count);
    }
    
    private static <TSource> Tuple2<Long, Integer> sumAndCountLong(Iterable<TSource> source, Func1<TSource, Long> selector) {
        long sum = 0;
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum += selector.func(sourceElement);
            count++;
        }
        return count == 0 ? new Tuple2<Long, Integer>(null, count) : new Tuple2<Long, Integer>(sum, count);
    }
    
    private static <TSource> Tuple2<Float, Integer> sumAndCountFloat(Iterable<TSource> source, Func1<TSource, Float> selector) {
        float sum = 0;
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum += selector.func(sourceElement);
            count++;
        }
        return count == 0 ? new Tuple2<Float, Integer>(null, count) : new Tuple2<Float, Integer>(sum, count);
    }
    
    private static <TSource> Tuple2<Double, Integer> sumAndCountDouble(Iterable<TSource> source, Func1<TSource, Double> selector) {
        double sum = 0;
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum += selector.func(sourceElement);
            count++;
        }
        return count == 0 ? new Tuple2<Double, Integer>(null, count) : new Tuple2<Double, Integer>(sum, count);
    }
    
    private static <TSource> Tuple2<BigInteger, Integer> sumAndCountBigInteger(Iterable<TSource> source, Func1<TSource, BigInteger> selector) {
        BigInteger sum = new BigInteger("0");
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum = sum.add(selector.func(sourceElement));
            count++;
        }
        return count == 0 ? new Tuple2<BigInteger, Integer>(null, count) : new Tuple2<BigInteger, Integer>(sum, count);
    }
    
    private static <TSource> Tuple2<BigDecimal, Integer> sumAndCountBigDecimal(Iterable<TSource> source, Func1<TSource, BigDecimal> selector) {
        BigDecimal sum = new BigDecimal(0);
        int count = 0;
        for(TSource sourceElement : source) {
            if(sourceElement == null) continue;
            sum = sum.add(selector.func(sourceElement));
            count++;
        }
        return count == 0 ? new Tuple2<BigDecimal, Integer>(null, count) : new Tuple2<BigDecimal, Integer>(sum, count);
    }

    private AggregateHelper() {
    }
}
