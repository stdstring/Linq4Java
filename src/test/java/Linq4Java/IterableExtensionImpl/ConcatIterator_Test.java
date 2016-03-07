/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Tuple1;
import IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class ConcatIterator_Test {
    
    @Test public void concatNonEmptySources() {
        List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
        iterables.add(nonEmptySource);
        iterables.add(nonEmptySource);
        Iterable<String> concatIterable = new ConcatIterable(iterables);
        String[] dest = IterableConverter.toArray(concatIterable, String.class);
        String[] expected = {"ab", "bc", "ab", "bc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void concatWithFirstEmptySources() {
        List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
        iterables.add(emptySource);
        iterables.add(nonEmptySource);
        iterables.add(nonEmptySource);
        Iterable<String> concatIterable = new ConcatIterable(iterables);
        String[] dest = IterableConverter.toArray(concatIterable, String.class);
        String[] expected = {"ab", "bc", "ab", "bc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void concatWithMiddleEmptySources() {
        List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
        iterables.add(nonEmptySource);
        iterables.add(emptySource);
        iterables.add(nonEmptySource);
        Iterable<String> concatIterable = new ConcatIterable(iterables);
        String[] dest = IterableConverter.toArray(concatIterable, String.class);
        String[] expected = {"ab", "bc", "ab", "bc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void concatWithLastEmptySources() {
        List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
        iterables.add(nonEmptySource);
        iterables.add(nonEmptySource);
        iterables.add(emptySource);
        Iterable<String> concatIterable = new ConcatIterable(iterables);
        String[] dest = IterableConverter.toArray(concatIterable, String.class);
        String[] expected = {"ab", "bc", "ab", "bc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void concatEmptySources() {
        List<Iterable<String>> iterables = new ArrayList<Iterable<String>>();
        iterables.add(emptySource);
        iterables.add(emptySource);
        Iterable<String> concatIterable = new ConcatIterable(iterables);
        String[] dest = IterableConverter.toArray(concatIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<String> nonEmptySource;
    {
        nonEmptySource = new ArrayList<String>();
        nonEmptySource.add("ab");
        nonEmptySource.add("bc");
    }

    private final List<String> emptySource;
    {
        emptySource = new ArrayList<String>();
    }
}
