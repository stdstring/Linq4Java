/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func0;
import Functional.Func1;
import Functional.Tuple2;
import IterableExtension.Grouping;
import IterableExtensionHelper.TrivialFuncs;
import IterableHelper.AssertExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author A.Ushakov
 */
public final class JoinIterator_Test {

    @Test public void join() {
        Func0<Iterator<Tuple2<String, String>>> factory = createJoinIteratorFactory(outerSource, innerSource);
        Iterable<Tuple2<String, String>> joinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, String>> expected = Arrays.asList(new Tuple2<String, String>("a111", "a999"),
                new Tuple2<String, String>("b222", "b888"),
                new Tuple2<String, String>("b223", "b888"),
                new Tuple2<String, String>("c333", "c777"),
                new Tuple2<String, String>("c333", "c776"));
        AssertExtension.assertIterableEquals(expected, joinIterable);
    }

    @Test public void joinWithMismatchedSources() {
        Func0<Iterator<Tuple2<String, String>>> factory = createJoinIteratorFactory(outerSource, innerSource2);
        Iterable<Tuple2<String, String>> joinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, String>> expected = new ArrayList<Tuple2<String, String>>();
        AssertExtension.assertIterableEquals(expected, joinIterable);
    }

    @Test public void joinWhenOuterEmpty() {
        Func0<Iterator<Tuple2<String, String>>> factory = createJoinIteratorFactory(emptySource, innerSource);
        Iterable<Tuple2<String, String>> joinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, String>> expected = new ArrayList<Tuple2<String, String>>();
        AssertExtension.assertIterableEquals(expected, joinIterable);
    }

    @Test public void joinWhenInnerEmpty() {
        Func0<Iterator<Tuple2<String, String>>> factory = createJoinIteratorFactory(outerSource, emptySource);
        Iterable<Tuple2<String, String>> joinIterable = new SimpleIterableImpl(factory);
        List<Tuple2<String, String>> expected = new ArrayList<Tuple2<String, String>>();
        AssertExtension.assertIterableEquals(expected, joinIterable);
    }

    private Func0<Iterator<Tuple2<String, String>>> createJoinIteratorFactory(final Iterable<String> outer, final Iterable<String> inner) {
        return new Func0<Iterator<Tuple2<String, String>>>() {
            @Override
            public Iterator<Tuple2<String, String>> func() {
                Iterable<Grouping<Character, String>> outerIterable = GroupHelper.createGroupIterable(outer, keySelector, TrivialFuncs.<String>getTrivialSelector());
                Map<Character, Grouping<Character, String>> innerMap = GroupHelper.createGroupMap(inner, keySelector, TrivialFuncs.<String>getTrivialSelector());
                return new JoinIterator(outerIterable.iterator(), innerMap);
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
