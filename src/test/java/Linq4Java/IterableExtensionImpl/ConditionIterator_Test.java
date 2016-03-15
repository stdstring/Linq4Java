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
public final class ConditionIterator_Test {

    @Test public void nonEmptyConditionResult() {
        Func1<String, Boolean> predicate = new Func1<String, Boolean>(){@Override public Boolean func(String param){return param.startsWith("aa");}};
        Iterable<String> conditionIterable = new ConditionIterable(source, FuncsAdapter.fakeParam2(predicate));
        String[] dest = IterableConverter.toArray(conditionIterable, String.class);
        String[] expected = {"aaabb", "aa34!56"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyConditionResult() {
        Func1<String, Boolean> predicate = new Func1<String, Boolean>(){@Override public Boolean func(String param){return param.startsWith("zz");}};
        Iterable<String> conditionIterable = new ConditionIterable(source, FuncsAdapter.fakeParam2(predicate));
        String[] dest = IterableConverter.toArray(conditionIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void nonEmptyIndexedConditionResult() {
        Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>(){@Override public Boolean func(String param, Integer index){return index < 2;}};
        Iterable<String> conditionIterable = new ConditionIterable(source, predicate);
        String[] dest = IterableConverter.toArray(conditionIterable, String.class);
        String[] expected = {"aaabb", "bbbcccc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void emptyIndexedConditionResult() {
        Func2<String, Integer, Boolean> predicate = new Func2<String, Integer, Boolean>(){@Override public Boolean func(String param, Integer index){return index > 10 ;}};
        Iterable<String> conditionIterable = new ConditionIterable(source, predicate);
        String[] dest = IterableConverter.toArray(conditionIterable, String.class);
        String[] expected = {};
        Assert.assertArrayEquals(expected, dest);
    }

    private final List<String> source;
    {
        source = new ArrayList<String>();
        source.add("aaabb");
        source.add("bbbcccc");
        source.add("caac12");
        source.add("aa34!56");
        source.add("dd??da");
    }
}