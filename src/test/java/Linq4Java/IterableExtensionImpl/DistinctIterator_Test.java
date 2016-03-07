/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author avu
 */
public final class DistinctIterator_Test {

    @Test public void nonEmptySource() {
        Iterable<String> distinctIterable = new DistinctIterable(nonEmptySource);
        String[] dest = IterableConverter.toArray(distinctIterable, String.class);
        String[] expected = {"aaa", "bbb", "aaa1"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptySource() {
        Iterable<String> distinctIterable = new DistinctIterable(emptySource);
        String[] dest = IterableConverter.toArray(distinctIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<String> nonEmptySource;
    {
        nonEmptySource = new ArrayList<String>();
        nonEmptySource.add("aaa");
        nonEmptySource.add("bbb");
        nonEmptySource.add("aaa");
        nonEmptySource.add("aaa1");
        nonEmptySource.add("bbb");
    }

    private final List<String> emptySource;
    {
        emptySource = new ArrayList<String>();
    }
}
