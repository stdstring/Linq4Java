package Linq4Java.Functional;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class Tuple2_Test {

    @Test public void equalsTest() {
        Assert.assertTrue(new Tuple2<String, Integer>("1", 1).equals(new Tuple2<String, Integer>("1", 1)));
        Assert.assertFalse(new Tuple2<String, Integer>("1", 1).equals(new Tuple2<String, Integer>("2", 1)));
        Assert.assertFalse(new Tuple2<String, Integer>("1", 1).equals(new Tuple2<String, Integer>("1", 2)));
        Assert.assertFalse(new Tuple2<String, Integer>("1", 1).equals(new Tuple2<Integer, String>(1, "1")));
        Assert.assertFalse(new Tuple2<String, Integer>("1", 1).equals(new Object()));
        Assert.assertNotSame(new Tuple2<String, Integer>("1", 1), new Tuple2<String, Integer>("1", 1));
    }

    @Test public void hashCodeTest() {
        Assert.assertTrue(new Tuple2<String, Integer>("1", 1).hashCode() == new Tuple2<String, Integer>("1", 1).hashCode());
        Assert.assertFalse(new Tuple2<String, Integer>("1", 1).hashCode() == new Tuple2<String, Integer>("3", 3).hashCode());
    }
}
