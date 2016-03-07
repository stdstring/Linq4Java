package IterableExtensionImpl;

import IterableExtensionHelper.SetOperationFuncs;
import IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author avu
 */
public final class SetOperationIterable_Test {

    @Test public void nonEmptyExceptNonEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(nonEmptySource1, nonEmptySource2, SetOperationFuncs.getExceptOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {"aaa1", "aaa2", "bbb1"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void nonEmptyExceptEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(nonEmptySource1, emptySource, SetOperationFuncs.getExceptOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {"aaa", "bbb", "aaa1", "aaa2", "bbb1"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyExceptNonEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(emptySource, nonEmptySource2, SetOperationFuncs.getExceptOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyExceptEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(emptySource, emptySource, SetOperationFuncs.getExceptOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void nonEmptyIntersectNonEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(nonEmptySource1, nonEmptySource2, SetOperationFuncs.getIntersectOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {"aaa", "bbb"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void nonEmptyIntersectEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(nonEmptySource1, emptySource, SetOperationFuncs.getIntersectOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyIntersectNonEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(emptySource, nonEmptySource2, SetOperationFuncs.getIntersectOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyIntersectEmpty() {
        Iterable<String> exceptIterable = new SetOperationIterable(emptySource, emptySource, SetOperationFuncs.getIntersectOp());
        String[] dest = IterableConverter.toArray(exceptIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<String> nonEmptySource1;
    {
        nonEmptySource1 = new ArrayList<String>();
        nonEmptySource1.add("aaa");
        nonEmptySource1.add("aaa");
        nonEmptySource1.add("bbb");
        nonEmptySource1.add("aaa1");
        nonEmptySource1.add("aaa2");
        nonEmptySource1.add("bbb1");
        nonEmptySource1.add("aaa2");
    }

    private final List<String> nonEmptySource2;
    {
        nonEmptySource2 = new ArrayList<String>();
        nonEmptySource2.add("aaa");
        nonEmptySource2.add("bbb");
        nonEmptySource2.add("aaa11");
        nonEmptySource2.add("aaa12");
        nonEmptySource2.add("bbb11");
    }

    private final List<String> emptySource;
    {
        emptySource = new ArrayList<String>();
    }
}
