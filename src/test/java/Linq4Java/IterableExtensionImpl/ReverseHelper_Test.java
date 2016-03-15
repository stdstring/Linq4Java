package Linq4Java.IterableExtensionImpl;

import Linq4Java.IterableHelper.IterableConverter;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public class ReverseHelper_Test {
    
    @Test public void reverse() {
        Integer[] dest = IterableConverter.toArray(ReverseHelper.createReverseIterable(source), Integer.class);
        Integer[] expected = {5, 4, 3, 2, 1};
        Assert.assertArrayEquals(expected, dest);
    }
    
    @Test public void reverseOnEmptySource() {
        Integer[] dest = IterableConverter.toArray(ReverseHelper.createReverseIterable(emptySource), Integer.class);
        Integer[] expected = {};
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

    private final List<Integer> emptySource;
    {
        emptySource = new ArrayList<Integer>();
    }
}
