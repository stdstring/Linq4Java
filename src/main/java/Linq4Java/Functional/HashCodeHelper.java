/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Functional;

/**
 *
 * @author A.Ushakov
 */
public final class HashCodeHelper {

    public static int hashCode(Object ... args) {
        int result = 0;
        for(Object arg : args) {
            result = (result ^ magicNumber) + (arg == null ? new Integer(0).hashCode() : arg.hashCode());
        }
        return result;
    }

    public static final int magicNumber = 13;

    private HashCodeHelper() {
    }
}
