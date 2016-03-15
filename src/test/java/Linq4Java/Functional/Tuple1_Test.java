package Linq4Java.Functional;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author std_string
 */
public final class Tuple1_Test {

    @Test public void equalsTest() {
        Assert.assertTrue(new Tuple1<String>("1").equals(new Tuple1<String>("1")));
        Assert.assertFalse(new Tuple1<String>("1").equals(new Tuple1<String>("2")));
        Assert.assertFalse(new Tuple1<String>("1").equals(new Tuple1<Integer>(1)));
        Assert.assertFalse(new Tuple1<String>("1").equals(new Object()));
        Assert.assertNotSame(new Tuple1<String>("1"), new Tuple1<String>("1"));
    }

    @Test public void hashCodeTest() {
        Assert.assertTrue(new Tuple1<String>("1").hashCode() == new Tuple1<String>("1").hashCode());
        Assert.assertFalse(new Tuple1<String>("1").hashCode() == new Tuple1<String>("2").hashCode());
    }
}
