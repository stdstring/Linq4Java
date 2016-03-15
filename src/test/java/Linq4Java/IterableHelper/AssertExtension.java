package Linq4Java.IterableHelper;

import Linq4Java.Functional.Action0;
import Linq4Java.IterableExtension.Grouping;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;

/**
 *
 * @author std_string
 */
public final class AssertExtension {

    public static <TException extends Throwable> void assertThrows(Action0 action, Class<TException> exceptionClass) {
        try {
            action.action();
            Assert.fail("Unexpected execution flow");
        }
        catch(Throwable e) {
            if(!exceptionClass.isInstance(e))
                Assert.fail("Unexpected exseption class");
        }
    }

    public static <TElement> void assertIterableEquals(Iterable<TElement> expected, Iterable<TElement> actual) {
        Iterator<TElement> expectedIterator = expected.iterator();
        Iterator<TElement> actualIterator = actual.iterator();
        while(expectedIterator.hasNext() && actualIterator.hasNext()) {
            Assert.assertEquals(expectedIterator.next(), actualIterator.next());
        }
        Assert.assertTrue(!expectedIterator.hasNext() && !actualIterator.hasNext());
    }
    
    public static <TKey, TElement> void assertGroupEquals(Iterable<Grouping<TKey, TElement>> expected, Iterable<Grouping<TKey, TElement>> actual) {
        Iterator<Grouping<TKey, TElement>> expectedIterator = expected.iterator();
        Iterator<Grouping<TKey, TElement>> actualIterator = actual.iterator();
        while(expectedIterator.hasNext() && actualIterator.hasNext()) {
            Grouping<TKey, TElement> expectedGrouping = expectedIterator.next();
            Grouping<TKey, TElement> actualGrouping = actualIterator.next();
            Assert.assertEquals(expectedGrouping.getKey(), actualGrouping.getKey());
            assertIterableEquals(expectedGrouping, actualGrouping);
        }
        Assert.assertTrue(!expectedIterator.hasNext() && !actualIterator.hasNext());
    }

    public static <TElement> void assertListEquals(List<TElement> expected, List<TElement> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        for(int index = 0; index < expected.size(); ++index) {
            Assert.assertEquals(expected.get(index), actual.get(index));
        }
    }

    public static <TKey, TElement> void assertMapEquals(Map<TKey, TElement> expected, Map<TKey, TElement> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        for(TKey key : expected.keySet()) {
            if(!actual.containsKey(key))
                Assert.fail("Different key's sets");
            Assert.assertEquals(expected.get(key), actual.get(key));
        }
    }

    private AssertExtension() {
    }
}
