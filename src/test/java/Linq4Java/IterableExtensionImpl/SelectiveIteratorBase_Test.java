/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionImpl;

import Functional.Action0;
import Functional.Func1;
import Functional.FuncsAdapter;
import IterableHelper.AssertExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author avu
 */
public class SelectiveIteratorBase_Test {

    @Test public void hasNextOnNonEmpty() {
        SelectiveIteratorTest iterator = new SelectiveIteratorTest(nonEmptySource.iterator(), predicate4NonEmptyResults);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("a", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("bb", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("e", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test public void nextOnNonEmpty() {
        final SelectiveIteratorTest iterator = new SelectiveIteratorTest(nonEmptySource.iterator(), predicate4NonEmptyResults);
        Assert.assertEquals("a", iterator.next());
        Assert.assertEquals("bb", iterator.next());
        Assert.assertEquals("e", iterator.next());
        AssertExtension.assertThrows(new Action0(){@Override public void action(){ iterator.next(); }}, NoSuchElementException.class);
    }

    @Test public void hasNextOnEmpty() {
        SelectiveIteratorTest iterator = new SelectiveIteratorTest(nonEmptySource.iterator(), predicate4EmptyResults);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test public void nextOnEmpty() {
        final SelectiveIteratorTest iterator = new SelectiveIteratorTest(nonEmptySource.iterator(), predicate4EmptyResults);
        AssertExtension.assertThrows(new Action0(){@Override public void action(){ iterator.next(); }}, NoSuchElementException.class);
    }
    
    private final Func1<String, Boolean> predicate4NonEmptyResults = new Func1<String, Boolean>() {
        @Override
        public Boolean func(String param) {
            return param.length() < 3;
        }
    };

    private final Func1<String, Boolean> predicate4EmptyResults = new Func1<String, Boolean>() {
        @Override
        public Boolean func(String param) {
            return param.length() > 10;
        }
    };

    private final List<String> nonEmptySource;
    {
        nonEmptySource = new ArrayList<String>();
        nonEmptySource.add("a");
        nonEmptySource.add("bb");
        nonEmptySource.add("ccc");
        nonEmptySource.add("dddd");
        nonEmptySource.add("e");
        nonEmptySource.add("ffff");
    }

    private class SelectiveIteratorTest<TSource> extends SelectiveIteratorBase<TSource> {
        public SelectiveIteratorTest(Iterator<TSource> iterator, Func1<TSource, Boolean> predicate) {
            super(iterator, FuncsAdapter.<TSource, Integer, Boolean>fakeParam2(predicate));
        }
    }
}
