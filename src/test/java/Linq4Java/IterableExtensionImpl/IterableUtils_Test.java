/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import IterableHelper.IterableConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author avu
 */
public class IterableUtils_Test {

    @Test public void nonEmptyRange() {
        Iterable<Integer> rangeIterable = IterableUtils.range(1, 3);
        Integer[] dest = IterableConverter.toArray(rangeIterable, Integer.class);
        Integer[] expected = {1, 2, 3};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyRange() {
        Iterable<Integer> rangeIterable1 = IterableUtils.range(1, 0);
        Iterable<Integer> rangeIterable2 = IterableUtils.range(1, -1);
        Integer[] dest1 = IterableConverter.toArray(rangeIterable1, Integer.class);
        Integer[] dest2 = IterableConverter.toArray(rangeIterable2, Integer.class);
        Integer[] expected = {};
        Assert.assertArrayEquals(expected, dest1);
        Assert.assertArrayEquals(expected, dest2);
    }

    @Test public void nonEmptyRepeat() {
        Iterable<String> repeatIterable = IterableUtils.repeat("abc", 3);
        String[] dest = IterableConverter.toArray(repeatIterable, String.class);
        String[] expected = {"abc", "abc", "abc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyRepeat() {
        Iterable<String> repeatIterable1 = IterableUtils.repeat("abc", 0);
        Iterable<String> repeatIterable2 = IterableUtils.repeat("abc", -1);
        String[] dest1 = IterableConverter.toArray(repeatIterable1, String.class);
        String[] dest2 = IterableConverter.toArray(repeatIterable2, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest1);
        Assert.assertArrayEquals(expected, dest2);
    }
}
