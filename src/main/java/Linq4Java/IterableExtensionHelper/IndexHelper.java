/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package IterableExtensionHelper;

/**
 *
 * @author avu
 */
// TODO : непонятно нужна ли подобная декомпозиция ?
public final class IndexHelper {

    public int getIndex() {
        return index;
    }

    public void updateIndex() {
        ++index;
    }

    private int index = 0;
}
