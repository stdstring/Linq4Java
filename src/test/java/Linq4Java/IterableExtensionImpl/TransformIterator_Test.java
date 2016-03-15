package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func1;
import Linq4Java.Functional.Func2;
import Linq4Java.Functional.FuncsAdapter;
import Linq4Java.IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class TransformIterator_Test {

    @Test public void transformValues() {
        Func1<Integer, Integer> selector = new Func1<Integer, Integer>(){@Override public Integer func(Integer param){return param*3 - 1;}};
        Iterable<Integer> transformIterable = new TransformIterable(source, FuncsAdapter.fakeParam2(selector));
        Integer[] dest = IterableConverter.toArray(transformIterable, Integer.class);
        Integer[] expected = {2, 5, 8, 11, 14};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void transformTypes() {
        Func1<Integer, String> selector = new Func1<Integer, String>(){@Override public String func(Integer param){return param.toString();}};
        Iterable<String> transformIterable = new TransformIterable(source, FuncsAdapter.fakeParam2(selector));
        String[] dest = IterableConverter.toArray(transformIterable, String.class);
        String[] expected = {"1", "2", "3", "4", "5"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void transformTypesWithIndex() {
        Func2<Integer, Integer, String> selector = new Func2<Integer, Integer, String>(){@Override public String func(Integer param1, Integer param2){return param2 + "|" + param1;}};
        Iterable<String> transformIterable = new TransformIterable(source, selector);
        String[] dest = IterableConverter.toArray(transformIterable, String.class);
        String[] expected = {"0|1", "1|2", "2|3", "3|4", "4|5"};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<Integer> source;
    {
        source = new ArrayList<Integer>();
        source.add(1);
        source.add(2);
        source.add(3);
        source.add(4);
        source.add(5);
    }
}
