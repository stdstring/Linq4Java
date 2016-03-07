/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func1;
import Functional.Func2;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author avu
 */
public final class AggregateHelper_Test {

    // TODO : вычисление среднего сделать менее коряво
    @Test public void aggregateOnNonEmptySource() {
        Func2<Integer, Integer, Integer> accumulateFunc = new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer func(Integer param1, Integer param2) {
                return param1 + param2;
            }
        };
        Func1<Integer, Double> resultSelector = new Func1<Integer, Double>() {
            @Override
            public Double func(Integer param) {
                return param * 1.0 / nonEmptySource.size();
            }
        };
        double average = AggregateHelper.aggregate(nonEmptySource, 0, accumulateFunc, resultSelector);
        Assert.assertEquals(45.0/5, average, 0);
    }

    // TODO : вычисление среднего сделать менее коряво
    @Test public void aggregateOnEmptySource() {
        Func2<Integer, Integer, Integer> accumulateFunc = new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer func(Integer param1, Integer param2) {
                return param1 + param2;
            }
        };
        Func1<Integer, Double> resultSelector = new Func1<Integer, Double>() {
            @Override
            public Double func(Integer param) {
                return param * 1.0;
            }
        };
        double average = AggregateHelper.aggregate(emptySource, 0, accumulateFunc, resultSelector);
        Assert.assertEquals(0.0, average, 0);
    }

    @Test public void successAllOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 0;
            }
        };
        Assert.assertTrue(AggregateHelper.all(nonEmptySource, predicate));
    }

    @Test public void failAllOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 10;
            }
        };
        Assert.assertFalse(AggregateHelper.all(nonEmptySource, predicate));
    }

    @Test public void allOnEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 10;
            }
        };
        Assert.assertTrue(AggregateHelper.all(emptySource, predicate));
    }

    @Test public void successAnyOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 20;
            }
        };
        Assert.assertTrue(AggregateHelper.any(nonEmptySource, predicate));
    }

    @Test public void failAnyOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 30;
            }
        };
        Assert.assertFalse(AggregateHelper.any(nonEmptySource, predicate));
    }

    @Test public void anyOnEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 10;
            }
        };
        Assert.assertFalse(AggregateHelper.any(emptySource, predicate));
    }

    @Test public void countNonZeroOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 12;
            }
        };
        Assert.assertEquals(2, AggregateHelper.count(nonEmptySource, predicate));
    }

    @Test public void countZeroOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 30;
            }
        };
        Assert.assertEquals(0, AggregateHelper.count(nonEmptySource, predicate));
    }

    @Test public void countOnEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 10;
            }
        };
        Assert.assertEquals(0, AggregateHelper.count(emptySource, predicate));
    }

    @Test public void longCountNonZeroOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 12;
            }
        };
        Assert.assertEquals(2, AggregateHelper.longCount(nonEmptySource, predicate));
    }

    @Test public void longCountZeroOnNonEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 30;
            }
        };
        Assert.assertEquals(0, AggregateHelper.longCount(nonEmptySource, predicate));
    }

    @Test public void longCountOnEmptySource() {
        Func1<Integer, Boolean> predicate = new Func1<Integer, Boolean>() {
            @Override
            public Boolean func(Integer param) {
                return param > 10;
            }
        };
        Assert.assertEquals(0, AggregateHelper.longCount(emptySource, predicate));
    }

    @Test public void eqlualsTwoSameSequences() {
        List<Integer> otherSource = new ArrayList<Integer>();
        otherSource.add(3);
        otherSource.add(13);
        otherSource.add(2);
        otherSource.add(5);
        otherSource.add(22);
        Assert.assertTrue(AggregateHelper.equals(nonEmptySource, otherSource));
        Assert.assertTrue(AggregateHelper.equals(emptySource, emptySource));
    }

    @Test public void eqlualsTwoSequencesWithDiffrerentItems() {
        List<Integer> otherSource = new ArrayList<Integer>();
        otherSource.add(3);
        otherSource.add(13);
        otherSource.add(3);
        otherSource.add(5);
        otherSource.add(22);
        Assert.assertFalse(AggregateHelper.equals(nonEmptySource, otherSource));
    }

    @Test public void eqlualsTwoSequencesWithDiffrerentLength() {
        List<Integer> otherSource = new ArrayList<Integer>();
        otherSource.add(3);
        otherSource.add(13);
        otherSource.add(3);
        otherSource.add(5);
        otherSource.add(22);
        otherSource.add(23);
        Assert.assertFalse(AggregateHelper.equals(nonEmptySource, otherSource));
        Assert.assertFalse(AggregateHelper.equals(emptySource, otherSource));
    }

    private final List<Integer> nonEmptySource;
    {
        nonEmptySource = new ArrayList<Integer>();
        nonEmptySource.add(3);
        nonEmptySource.add(13);
        nonEmptySource.add(2);
        nonEmptySource.add(5);
        nonEmptySource.add(22);
    }

    private final List<Integer> emptySource;
    {
        emptySource = new ArrayList<Integer>();
    }
}
