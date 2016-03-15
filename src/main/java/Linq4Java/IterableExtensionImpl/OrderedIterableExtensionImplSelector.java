package Linq4Java.IterableExtensionImpl;

import Linq4Java.IterableExtension.OrderedIterable;

/**
 *
 * @author std_string
 */
final class OrderedIterableExtensionImplSelector {
    
    public static <TSource> OrderedIterableExtensionImpl<TSource> getImpl(OrderedIterable<TSource> orderedIterable) {
        return new OrderedIterableExtensionImpl<TSource>(orderedIterable);
    }

    private OrderedIterableExtensionImplSelector() {
    }
}
