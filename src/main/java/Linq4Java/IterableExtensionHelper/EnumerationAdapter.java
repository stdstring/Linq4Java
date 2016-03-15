package Linq4Java.IterableExtensionHelper;

import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 * @author std_string
 */
public class EnumerationAdapter<TElement> implements Enumeration<TElement> {

    public EnumerationAdapter(Iterator<TElement> sourceIterator) {
        this.sourceIterator = sourceIterator;
    }

    @Override
    public boolean hasMoreElements() {
        return sourceIterator.hasNext();
    }

    @Override
    public TElement nextElement() {
        return sourceIterator.next();
    }

    private final Iterator<TElement> sourceIterator;
}
