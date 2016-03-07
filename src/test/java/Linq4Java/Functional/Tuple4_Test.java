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
public final class Tuple4_Test {

    @Test public void equalsTest() {
        Assert.assertTrue(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<String, Integer, Integer, Integer>("2", 1, 1, 1)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<String, Integer, Integer, Integer>("1", 2, 1, 1)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<String, Integer, Integer, Integer>("1", 1, 2, 1)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 2)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Tuple4<Integer, String, Integer, Integer>(1, "1", 1, 1)));
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).equals(new Object()));
        Assert.assertNotSame(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1), new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1));
    }

    @Test public void hashCodeTest() {
        Assert.assertTrue(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).hashCode() == new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).hashCode());
        Assert.assertFalse(new Tuple4<String, Integer, Integer, Integer>("1", 1, 1, 1).hashCode() == new Tuple4<String, Integer, Integer, Integer>("3", 3, 3, 3).hashCode());
    }
}
