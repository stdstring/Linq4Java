/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Func0;
import Functional.Func2;
import IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author A.Ushakov
 */
public final class SkipIterator_Test {

    @Test public void skipWhileWithNonEmptyResult() {
        final Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>() {
            @Override
            public Boolean func(String param1, Integer param2) {
                return param1.length() < 3;
            }
        };
        Func0<Iterator<String>> skipIteratorFactory = new Func0<Iterator<String>>() {
            @Override
            public Iterator<String> func() {
                return new SkipIterator(source.iterator(), predicate);
            }
        };
        Iterable<String> skipWhileIterable = new SimpleIterableImpl(skipIteratorFactory);
        String[] dest = IterableConverter.toArray(skipWhileIterable, String.class);
        String[] expected = {"ccc", "d", "eeeee"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skipWhileWithEmptyResult() {
        final Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>() {
            @Override
            public Boolean func(String param1, Integer param2) {
                return param1.length() < 10;
            }
        };
        Func0<Iterator<String>> skipIteratorFactory = new Func0<Iterator<String>>() {
            @Override
            public Iterator<String> func() {
                return new SkipIterator(source.iterator(), predicate);
            }
        };
        Iterable<String> skipWhileIterable = new SimpleIterableImpl(skipIteratorFactory);
        String[] dest = IterableConverter.toArray(skipWhileIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skipWithNonEmptyResult() {
        final Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>() {
            @Override
            public Boolean func(String param1, Integer param2) {
                return param2 < count;
            }

            private final int count = 2;
        };
        Func0<Iterator<String>> skipIteratorFactory = new Func0<Iterator<String>>() {
            @Override
            public Iterator<String> func() {
                return new SkipIterator(source.iterator(), predicate);
            }
        };
        Iterable<String> skipWhileIterable = new SimpleIterableImpl(skipIteratorFactory);
        String[] dest = IterableConverter.toArray(skipWhileIterable, String.class);
        String[] expected = {"ccc", "d", "eeeee"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void skipWithEmptyResult() {
        final Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>() {
            @Override
            public Boolean func(String param1, Integer param2) {
                return param2 < count;
            }

            private final int count = 10;
        };
        Func0<Iterator<String>> skipIteratorFactory = new Func0<Iterator<String>>() {
            @Override
            public Iterator<String> func() {
                return new SkipIterator(source.iterator(), predicate);
            }
        };
        Iterable<String> skipWhileIterable = new SimpleIterableImpl(skipIteratorFactory);
        String[] dest = IterableConverter.toArray(skipWhileIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<String> source;
    {
        source = new ArrayList<String>();
        source.add("a");
        source.add("bb");
        source.add("ccc");
        source.add("d");
        source.add("eeeee");
    }
}
