package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func1;
import Linq4Java.IterableExtension.Grouping;
import Linq4Java.IterableExtensionHelper.TrivialFuncs;
import Linq4Java.IterableHelper.GroupingConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public class GroupHelper_Test {

    @Test public void createGroupIterable() {
        Iterable<Grouping<Integer, String>> groupIterable = GroupHelper.createGroupIterable(source, keySelector, TrivialFuncs.<String>getTrivialSelector());
        Integer[] destKeys = GroupingConverter.toKeyArray(groupIterable, Integer.class);
        Integer[] expectedKeys = {1, 3, 2, 4};
        Assert.assertArrayEquals(expectedKeys, destKeys);
        String[] destItems = GroupingConverter.toItemArray(groupIterable, String.class);
        String[] expectedItems = {"a", "g", "bbb", "cc", "dd", "ff", "eeee"};
        Assert.assertArrayEquals(expectedItems, destItems);
    }

    @Test public void createGroupIterableOnEmptySource() {
        Iterable<Grouping<Integer, String>> groupIterable = GroupHelper.createGroupIterable(emptySource, keySelector, TrivialFuncs.<String>getTrivialSelector());
        Integer[] destKeys = GroupingConverter.toKeyArray(groupIterable, Integer.class);
        Integer[] expectedKeys = {};
        Assert.assertArrayEquals(expectedKeys, destKeys);
        String[] destItems = GroupingConverter.toItemArray(groupIterable, String.class);
        String[] expectedItems = {};
        Assert.assertArrayEquals(expectedItems, destItems);
    }

    private final List<String> source;
    {
        source = new ArrayList<String>();
        source.add("a");
        source.add("bbb");
        source.add("cc");
        source.add("dd");
        source.add("eeee");
        source.add("ff");
        source.add("g");
    }

    private final List<String> emptySource;
    {
        emptySource = new ArrayList<String>();
    }

    private final Func1<String, Integer> keySelector = new Func1<String, Integer>() {
        @Override
        public Integer func(String param) {
            return param.length();
        }
    };
}
