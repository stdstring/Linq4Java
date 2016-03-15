package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func1;
import Linq4Java.IterableHelper.AssertExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class ConvertHelper_Test {

    @Test public void toArray() {
        String[] dest = ConvertHelper.toArray(source, String.class);
        String[] expected = {"a", "bb", "ccc"};
        Assert.assertArrayEquals(expected, dest);
    }

    @Test public void toList() {
        List<String> dest = ConvertHelper.toList(source);
        List<String> expected = new ArrayList();
        expected.add("a");
        expected.add("bb");
        expected.add("ccc");
        AssertExtension.assertListEquals(expected, dest);
    }

    @Test public void toMap() {
        Map<String, String> dest = ConvertHelper.toMap(source, keySelector);
        Map<String, String> expected = new HashMap<String, String>();
        expected.put("a", "a");
        expected.put("b", "bb");
        expected.put("c", "ccc");
        AssertExtension.assertMapEquals(expected, dest);
    }

    @Test public void toMapWithElementSelector() {
        Map<String, Integer> dest = ConvertHelper.toMap(source, keySelector, elementSelector);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("a", 1);
        expected.put("b", 2);
        expected.put("c", 3);
        AssertExtension.assertMapEquals(expected, dest);
    }

    private final List<String> source;
    {
        source = new ArrayList<String>();
        source.add("a");
        source.add("bb");
        source.add("ccc");
    }

    private final Func1<String, String> keySelector = new Func1<String, String>() {
        @Override
        public String func(String param) {
            return param.substring(0, 1);
        }
    };

    private final Func1<String, Integer> elementSelector = new Func1<String, Integer>() {
        @Override
        public Integer func(String param) {
            return param.length();
        }
    };
}
