package Linq4Java.IterableExtensionHelper;

import Linq4Java.Functional.Func1;
import Linq4Java.Functional.Tuple2;
import java.util.Comparator;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class ComparatorFactory_Test {
    
    @Test public void comparator4Comparable() {
        Comparator<String> ascendingComparator = ComparatorFactory.create(true);
        Assert.assertTrue(ascendingComparator.compare("abc", "abc") == 0);
        Assert.assertTrue(ascendingComparator.compare("abc", "abd") < 0);
        Assert.assertTrue(ascendingComparator.compare("abc", "abb") > 0);
        Comparator<String> descendingComparator = ComparatorFactory.create(false);
        Assert.assertTrue(descendingComparator.compare("abc", "abc") == 0);
        Assert.assertTrue(descendingComparator.compare("abc", "abd") > 0);
        Assert.assertTrue(descendingComparator.compare("abc", "abb") < 0);
    }
    
    @Test public void comparator4ComparableKey() {
        Comparator<TestItem> ascendingKeyComparator = ComparatorFactory.create(keySelector, true);
        Assert.assertTrue(ascendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(10, "abc")) == 0);
        Assert.assertTrue(ascendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(11, "abc")) < 0);
        Assert.assertTrue(ascendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(9, "abc")) > 0);
        Comparator<TestItem> descendingKeyComparator = ComparatorFactory.create(keySelector, false);
        Assert.assertTrue(descendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(10, "abc")) == 0);
        Assert.assertTrue(descendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(11, "abc")) > 0);
        Assert.assertTrue(descendingKeyComparator.compare(new TestItem(10, "abc"), new TestItem(9, "abc")) < 0);
    }
    
    @Test public void comparator4KeyWithComparator() {
        Comparator<TestItem> comparator = ComparatorFactory.create(keySelector, keyComparator);
        Assert.assertTrue(comparator.compare(new TestItem(10, "abc"), new TestItem(10, "abc")) == 0);
        Assert.assertTrue(comparator.compare(new TestItem(10, "abc"), new TestItem(11, "abc")) > 0);
        Assert.assertTrue(comparator.compare(new TestItem(10, "abc"), new TestItem(9, "abc")) < 0);
    }
    
    @Test public void descendingComparator() {
        Comparator<Integer> comparator = ComparatorFactory.createDescending(ComparatorFactory.<Integer>create(true));
        Assert.assertTrue(comparator.compare(10, 10) == 0);
        Assert.assertTrue(comparator.compare(10, 11) > 0);
        Assert.assertTrue(comparator.compare(11, 10) < 0);
    }
    
    private final Func1<TestItem, Integer> keySelector = new Func1<TestItem, Integer>() {
        @Override
        public Integer func(TestItem param) { return param.getKey(); }        
    };
    
    private final Comparator<Integer> keyComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) { return o2 - o1; }
    };

    private class TestItem extends Tuple2<Integer, String> {
        public TestItem(int key, String value) {
            super(key, value);
        }

        public int getKey() {
            return getItem1();
        }

        public String getValue() {
            return getItem2();
        }
    }
}
