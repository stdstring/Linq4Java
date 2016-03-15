package Linq4Java.IterableExtensionImpl;

import Linq4Java.IterableExtensionHelper.EnumerationAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public class OrderHelper_Test {
    @Test public void orderEmptySource() {
        Iterable<Iterable<TestItem>> orderedEmptySource = OrderHelper.order(emptySource, comparator);
        List<Iterable<TestItem>> dest = Collections.list(new EnumerationAdapter<Iterable<TestItem>>(orderedEmptySource.iterator()));
        Assert.assertEquals(0, dest.size());
    }

    @Test public void orderManyItemsSource() {
        Iterable<Iterable<TestItem>> orderedSource = OrderHelper.order(source, comparator);
        List<Iterable<TestItem>> dest = Collections.list(new EnumerationAdapter<Iterable<TestItem>>(orderedSource.iterator()));
        Assert.assertEquals(6, dest.size());
        CheckPortion(dest.get(0), new TestItem[]{new TestItem(7, "seven2"), new TestItem(7, "seven")});
        CheckPortion(dest.get(1), new TestItem[]{new TestItem(11, "eleven")});
        CheckPortion(dest.get(2), new TestItem[]{new TestItem(1, "one"), new TestItem(1, "one2")});
        CheckPortion(dest.get(3), new TestItem[]{new TestItem(8, "eight"), new TestItem(8, "eight_666")});
        CheckPortion(dest.get(4), new TestItem[]{new TestItem(9, "nine")});
        CheckPortion(dest.get(5), new TestItem[]{new TestItem(11, "eleven")});
    }
    
    private static void CheckPortion(Iterable<TestItem> actual, TestItem[] expected) {
        TestItem[] actualArray = Collections.list(new EnumerationAdapter<TestItem>(actual.iterator())).toArray(new TestItem[0]);
        Assert.assertArrayEquals(actualArray, expected);
    }
    
    private class TestItem {
        public TestItem(Integer number, String description) {
            this.number = number;
            this.description = description;
        }
        
        public Integer getNumber() {
            return number;
        }
        
        public String getDescription() {
            return description;
        }
        
        @Override
        public boolean equals(Object other) {
            if(other == null || other.getClass() != getClass()) return false;
            TestItem otherItem = (TestItem)other;
            return number.equals(otherItem.number) && description.equals(otherItem.description);
        }
        
        @Override
        public int hashCode() {
            return number.hashCode() ^ description.hashCode();
        }
        
        private final Integer number;
        private final String description;
    }
    
    private final List<Iterable<TestItem>> emptySource;
    {
        emptySource = new ArrayList<Iterable<TestItem>>();
    }

    private final List<Iterable<TestItem>> source;
    {
        source = new ArrayList<Iterable<TestItem>>();
        List<TestItem> item = new ArrayList<TestItem>();
        source.add(item);
        item.add(new TestItem(11, "eleven"));
        item.add(new TestItem(7, "seven2"));
        item.add(new TestItem(7, "seven"));
        List<TestItem> item2 = new ArrayList<TestItem>();
        source.add(item2);
        item2.add(new TestItem(8, "eight"));
        item2.add(new TestItem(1, "one"));
        item2.add(new TestItem(9, "nine"));
        item2.add(new TestItem(1, "one2"));
        item2.add(new TestItem(8, "eight_666"));
        item2.add(new TestItem(11, "eleven"));
    }
    
    Comparator<TestItem> comparator = new Comparator<TestItem>() {
        @Override
        public int compare(TestItem item1, TestItem item2) {
            if(item1 == null && item2 != null) return -1;
            if(item1 != null && item2 == null) return 1;
            if(item1 == null && item2 == null) return 0;
            if(item1.getNumber() == null && item2.getNumber() != null) return -1;
            if(item1.getNumber() != null && item2.getNumber() == null) return 1;
            return item1.getNumber().compareTo(item2.getNumber());
        }
    };
}
