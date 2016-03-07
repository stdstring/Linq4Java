/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IterableExtensionImpl;

import IterableExtension.OrderedIterable;

/**
 *
 * @author A.Ushakov
 */
final class OrderedIterableExtensionImplSelector {
    
    public static <TSource> OrderedIterableExtensionImpl<TSource> getImpl(OrderedIterable<TSource> orderedIterable) {
        return new OrderedIterableExtensionImpl<TSource>(orderedIterable);
    }

    private OrderedIterableExtensionImplSelector() {
    }
}
