/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Functional;

/**
 *
 * @author A.Ushakov
 */
public final class EqualsHelper {
    
    public static <TSource> boolean equals(TSource item1, TSource item2) {
        if(item1 == null) return item2 == null;
        return item1.equals(item2);
    }
    
    private EqualsHelper() {
    }
}
