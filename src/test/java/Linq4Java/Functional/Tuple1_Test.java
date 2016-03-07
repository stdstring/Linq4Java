/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Functional;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author A.Ushakov
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
