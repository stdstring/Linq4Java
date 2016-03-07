/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import Functional.Func1;
import IterableExtension.OrderedIterable;
import IterableExtensionHelper.ComparatorFactory;
import IterableExtensionHelper.EnumerationAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author A.Ushakov
 */
public class OrderedIterableImpl_Test {
    
    @Test public void orderEmpty() {
        OrderedIterable<TestData> orderedData = OrderedIterableImpl.createOrderedIterable(source, key1Selector, ComparatorFactory.<String>create(true));
        checkPortion(orderedData, new TestData[0]);
    }
    
    @Test public void singleAscendingOrder() {
        OrderedIterable<TestData> orderedData = OrderedIterableImpl.createOrderedIterable(source, key1Selector, ComparatorFactory.<String>create(true));
        fillData(source);
        TestData[] expected = { new TestData("AAA", 0), new TestData("AAA", 1), new TestData("ABB", 3), new TestData("ABB", 8), new TestData("ABB", 1), new TestData("ABC", 11), new TestData("ABC", 7), new TestData("ADD", 666) };
        checkPortion(orderedData, expected);
    }
    
    @Test public void singleDescendingOrder() {
        OrderedIterable<TestData> orderedData = OrderedIterableImpl.createOrderedIterable(source, key1Selector, ComparatorFactory.<String>create(false));
        fillData(source);
        TestData[] expected = { new TestData("ADD", 666), new TestData("ABC", 11), new TestData("ABC", 7), new TestData("ABB", 3), new TestData("ABB", 8), new TestData("ABB", 1), new TestData("AAA", 0), new TestData("AAA", 1) };
        checkPortion(orderedData, expected);
    }
    
    @Test public void doubleAscendingOrder() {
        OrderedIterable<TestData> orderedData = OrderedIterableImpl.createOrderedIterable(source, key1Selector, ComparatorFactory.<String>create(true));
        fillData(source);
        TestData[] firstOrderExpected = { new TestData("AAA", 0), new TestData("AAA", 1), new TestData("ABB", 3), new TestData("ABB", 8), new TestData("ABB", 1), new TestData("ABC", 11), new TestData("ABC", 7), new TestData("ADD", 666) };
        checkPortion(orderedData, firstOrderExpected);
        TestData[] secondOrderExpected = { new TestData("AAA", 0), new TestData("AAA", 1), new TestData("ABB", 1), new TestData("ABB", 3), new TestData("ABB", 8), new TestData("ABC", 7), new TestData("ABC", 11), new TestData("ADD", 666) };
        checkPortion(orderedData.createOrderedIterable(key2Selector, ComparatorFactory.<Integer>create(true), false), secondOrderExpected);
    }
    
    @Test public void doubleDescendingOrder() {
        OrderedIterable<TestData> orderedData = OrderedIterableImpl.createOrderedIterable(source, key1Selector, ComparatorFactory.<String>create(false));
        fillData(source);
        TestData[] firstOrderExpected = { new TestData("ADD", 666), new TestData("ABC", 11), new TestData("ABC", 7), new TestData("ABB", 3), new TestData("ABB", 8), new TestData("ABB", 1), new TestData("AAA", 0), new TestData("AAA", 1) };
        checkPortion(orderedData, firstOrderExpected);
        TestData[] secondOrderExpected = { new TestData("ADD", 666), new TestData("ABC", 11), new TestData("ABC", 7), new TestData("ABB", 8), new TestData("ABB", 3), new TestData("ABB", 1), new TestData("AAA", 1), new TestData("AAA", 0) };
        checkPortion(orderedData.createOrderedIterable(key2Selector, ComparatorFactory.<Integer>create(true), true), secondOrderExpected);
    }
    
    private static void checkPortion(Iterable<TestData> actual, TestData[] expected) {
        TestData[] actualArray = Collections.list(new EnumerationAdapter<TestData>(actual.iterator())).toArray(new TestData[0]);
        Assert.assertArrayEquals(actualArray, expected);
    }
    
    private static List<TestData> fillData(List<TestData> storage) {
        storage.clear();
        storage.add(new TestData("ABC", 11));
        storage.add(new TestData("ABB", 3));
        storage.add(new TestData("ADD", 666));
        storage.add(new TestData("ABC", 7));
        storage.add(new TestData("AAA", 0));
        storage.add(new TestData("ABB", 8));
        storage.add(new TestData("AAA", 1));
        storage.add(new TestData("ABB", 1));
        return storage;
    }
    
    private static class TestData {
        public TestData(String key1, Integer key2) {
            this.key1 = key1;
            this.key2 = key2;
        }
        
        public String getKey1() {
            return key1;
        }
        
        public Integer getKey2() {
            return key2;
        }
        
        @Override
        public boolean equals(Object other) {
            if(other == null || other.getClass() != getClass()) return false;
            TestData otherItem = (TestData)other;
            return key1.equals(otherItem.key1) && key2.equals(otherItem.key2);
        }
        
        @Override
        public int hashCode() {
            return key1.hashCode() ^ key2.hashCode();
        }
        
        private final String key1;
        private final Integer key2;
    }

    private final List<TestData> source = new ArrayList<TestData>();
    
    private final Func1<TestData, String> key1Selector = new Func1<TestData, String>() {
        @Override
        public String func(TestData source) {
            return source.getKey1();
        }
    };
    
    private final Func1<TestData, Integer> key2Selector = new Func1<TestData, Integer>() {
        @Override
        public Integer func(TestData source) {
            return source.getKey2();
        }
    };
}
