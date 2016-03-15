package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import Linq4Java.Functional.Func1;
import Linq4Java.Functional.Tuple2;
import Linq4Java.IterableExtension.Grouping;
import Linq4Java.IterableExtensionHelper.TrivialFuncs;
import Linq4Java.IterableHelper.AssertExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class GroupJoinIterator_Test {

    @Test public void groupJoin() {
        Func0<Iterator<Tuple2<String, Iterable<String>>>> factory = createGroupJoinIteratorFactory(outerSource, innerSource);
        Iterable<Tuple2<String, Iterable<String>>> groupJoinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, Iterable<String>>> expected = Arrays.asList(new Tuple2<String, Iterable<String>>("a111", Arrays.asList("a999")),
                new Tuple2<String, Iterable<String>>("b222", Arrays.asList("b888")),
                new Tuple2<String, Iterable<String>>("b223", Arrays.asList("b888")),
                new Tuple2<String, Iterable<String>>("c333", Arrays.asList("c777", "c776")),
                new Tuple2<String, Iterable<String>>("d444", Arrays.<String>asList()));
        checkResults(expected, groupJoinIterable);
    }
    
    @Test public void groupJoinWithMismatchedSources() {
        Func0<Iterator<Tuple2<String, Iterable<String>>>> factory = createGroupJoinIteratorFactory(outerSource, innerSource2);
        Iterable<Tuple2<String, Iterable<String>>> groupJoinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, Iterable<String>>> expected = Arrays.asList(new Tuple2<String, Iterable<String>>("a111", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("b222", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("b223", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("c333", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("d444", Arrays.<String>asList()));
        checkResults(expected, groupJoinIterable);
    }
    
    @Test public void groupJoinWhenOuterEmpty() {
        Func0<Iterator<Tuple2<String, Iterable<String>>>> factory = createGroupJoinIteratorFactory(emptySource, innerSource);
        Iterable<Tuple2<String, Iterable<String>>> groupJoinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, Iterable<String>>> expected = new ArrayList<Tuple2<String, Iterable<String>>>();
        checkResults(expected, groupJoinIterable);
    }

    @Test public void groupJoinWhenInnerEmpty() {
        Func0<Iterator<Tuple2<String, Iterable<String>>>> factory = createGroupJoinIteratorFactory(outerSource, emptySource);
        Iterable<Tuple2<String, Iterable<String>>> groupJoinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, Iterable<String>>> expected = Arrays.asList(new Tuple2<String, Iterable<String>>("a111", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("b222", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("b223", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("c333", Arrays.<String>asList()),
                new Tuple2<String, Iterable<String>>("d444", Arrays.<String>asList()));
        checkResults(expected, groupJoinIterable);
    }

    private void checkResults(Iterable<Tuple2<String, Iterable<String>>> expected, Iterable<Tuple2<String, Iterable<String>>> actual) {
        Iterator<Tuple2<String, Iterable<String>>> expectedIterator = expected.iterator();
        Iterator<Tuple2<String, Iterable<String>>> actualIterator = actual.iterator();
        while(expectedIterator.hasNext() && actualIterator.hasNext()) {
            Tuple2<String, Iterable<String>> expectedItem = expectedIterator.next();
            Tuple2<String, Iterable<String>> actualItem = actualIterator.next();
            Assert.assertEquals(expectedItem.getItem1(), actualItem.getItem1());
            AssertExtension.assertIterableEquals(expectedItem.getItem2(), actualItem.getItem2());
        }
        Assert.assertTrue(!expectedIterator.hasNext() && !actualIterator.hasNext());
    }

    private Func0<Iterator<Tuple2<String, Iterable<String>>>> createGroupJoinIteratorFactory(final Iterable<String> outer, final Iterable<String> inner) {
        return new Func0<Iterator<Tuple2<String, Iterable<String>>>>() {
            @Override
            public Iterator<Tuple2<String, Iterable<String>>> func() {
                Iterable<Grouping<Character, String>> outerIterable = GroupHelper.createGroupIterable(outer, keySelector, TrivialFuncs.<String>getTrivialSelector());
                Map<Character, Grouping<Character, String>> innerMap = GroupHelper.createGroupMap(inner, keySelector, TrivialFuncs.<String>getTrivialSelector());
                return new GroupJoinIterator(outerIterable.iterator(), innerMap);
            }
        };
    }

    private final Func1<String, Character> keySelector = new Func1<String, Character>() {
        @Override
        public Character func(String param) {
            return param.charAt(0);
        }
    };

    private final List<String> outerSource = new ArrayList<String>();
    {
        outerSource.add("a111");
        outerSource.add("b222");
        outerSource.add("b223");
        outerSource.add("c333");
        outerSource.add("d444");
    }

    private final List<String> innerSource = new ArrayList<String>();
    {
        innerSource.add("a999");
        innerSource.add("b888");
        innerSource.add("c777");
        innerSource.add("c776");
        innerSource.add("e666");
    }

    private final List<String> innerSource2 = new ArrayList<String>();
    {
        innerSource2.add("w999");
        innerSource2.add("x888");
        innerSource2.add("y777");
        innerSource2.add("y776");
        innerSource2.add("Z666");
    }

    private final List<String> emptySource = new ArrayList<String>();
}
