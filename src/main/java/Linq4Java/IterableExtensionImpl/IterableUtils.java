package Linq4Java.IterableExtensionImpl;

import Linq4Java.Functional.Func0;
import Linq4Java.Functional.Func2;
import Linq4Java.IterableExtension.IterableExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author std_string
 */
public class IterableUtils {

    public static <TSource> IterableExtension<TSource> createQuery(Iterable<TSource> source) {
        return new IterableExtensionImpl(source);
    }
    
    public static <TSource> IterableExtension<TSource> createCastQuery(Iterable source) {
        return new IterableExtensionImpl(source);
    }
    
    public static <TSource> IterableExtension<TSource> createOfTypeQuery(final Iterable source, final Class<TSource> elementClass) {
        Func0<Iterator<TSource>> iteratorFactory = new Func0<Iterator<TSource>>() {
            @Override
            public Iterator<TSource> func() {
                Func2<Object, Integer, Boolean> predicate = new Func2<Object, Integer, Boolean>() {
                    @Override
                    public Boolean func(Object param1, Integer param2) {
                        return param1.getClass().equals(elementClass);
                    }
                };
                Iterator<Object> iterator = new SelectiveIteratorBase(source.iterator(), predicate);
                Func2<Object, Integer, TSource> selector = new Func2<Object, Integer, TSource>() {
                    @Override
                    public TSource func(Object param1, Integer param2) {
                        return (TSource)param1;
                    }                    
                };
                return new TransformIterator(iterator, selector);
            }            
        };
        return new IterableExtensionImpl(new SimpleIterableImpl(iteratorFactory));
    }
    
    public static <TSource> IterableExtension<TSource> empty() {
        return new IterableExtensionImpl<TSource>(new ArrayList<TSource>());
    }

    public static IterableExtension<Integer> range(final int start, final int count) {
        Func0<Iterator<Integer>> iteratorFactory = new Func0<Iterator<Integer>>() {
            @Override
            public Iterator<Integer> func() {
                return new Iterator<Integer>() {
                    @Override
                    public boolean hasNext() {
                        return index < count;
                    }

                    @Override
                    public Integer next() {
                        if(hasNext())
                            return start + (index++);
                        throw new NoSuchElementException();
                    }

                    @Override
                    public final void remove() {
                        throw new UnsupportedOperationException();
                    }

                    private int index = 0;
                };
            }
        };
        return new IterableExtensionImpl(new SimpleIterableImpl(iteratorFactory));
    }

    public static <TSource> IterableExtension<TSource> repeat(final TSource element, final int count) {
        Func0<Iterator<TSource>> iteratorFactory = new Func0<Iterator<TSource>>() {
            @Override
            public Iterator<TSource> func() {
                return new Iterator<TSource>() {
                    @Override
                    public boolean hasNext() {
                        return index < count;
                    }

                    @Override
                    public TSource next() {
                        if(hasNext()) {
                            ++index;
                            return element;
                        }
                        throw new NoSuchElementException();
                    }

                    @Override
                    public final void remove() {
                        throw new UnsupportedOperationException();
                    }

                    private int index = 0;
                };
            }
        };
        return new IterableExtensionImpl(new SimpleIterableImpl(iteratorFactory));
    }

    private IterableUtils() {
    }
}
