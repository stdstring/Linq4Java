/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtension;

/**
 *
 * @author aushakov
 */
public interface Grouping<TKey, TElement> extends Iterable<TElement> {
    TKey getKey();
}
