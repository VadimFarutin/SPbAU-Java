package ru.spbau.farutin.homework06;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Collections - presents methods operating with iterables and collections.
 */
public class Collections {
    /**
     * Generates new list applying function to all elements.
     * @param f function to apply
     * @param a where to take elements from
     * @param <T> type of elements
     * @param <U> type of new elements
     * @return list with new elements
     */
    public static @NotNull <T, U> List<U> map(@NotNull Function1<? super T, ? extends U> f,
                                              @NotNull Iterable<T> a) {
        ArrayList<U> list = new ArrayList<>();

        for (T element : a) {
            list.add(f.apply(element));
        }

        return list;
    }

    /**
     * Generates new list taking elements which satisfy given predicate.
     * @param p predicate to satisfy
     * @param a where to take elements from
     * @param <T> type of elements
     * @return list with new elements
     */
    public static @NotNull <T> List<T> filter(@NotNull Predicate<? super T> p,
                                              @NotNull Iterable<T> a) {
        ArrayList<T> list = new ArrayList<>();

        for (T element : a) {
            if (p.apply(element)) {
                list.add(element);
            }
        }

        return list;
    }

    /**
     * Generates new list taking elements until one of them does not satisfy predicate.
     * @param p predicate to satisfy
     * @param a where to take elements from
     * @param <T> type of elements
     * @return list with new elements
     */
    public static @NotNull <T> List<T> takeWhile(@NotNull Predicate<? super T> p,
                                                 @NotNull Iterable<T> a) {
        ArrayList<T> list = new ArrayList<>();

        for (T element : a) {
            if (!p.apply(element)) {
                break;
            }

            list.add(element);
        }

        return list;
    }

    /**
     * Generates new list taking elements until one of them satisfies predicate.
     * @param p predicate to satisfy
     * @param a where to take elements from
     * @param <T> type of elements
     * @return list with new elements
     */
    public static @NotNull <T> List<T> takeUnless(@NotNull Predicate<? super T> p,
                                                  @NotNull Iterable<T> a) {
        return takeWhile(p.not(), a);
    }

    /**
     * Right folding of collection.
     * @param f function to fold with
     * @param z initial value
     * @param a collection to fold
     * @param <T> type of elements
     * @param <U> type of return value
     * @return value of folded collection
     */
    public static @NotNull <T, U> U foldr(@NotNull Function2<? super T, ? super U, ? extends U> f,
                                          U z,
                                          @NotNull Collection<T> a) {
        return foldrByIterator(f, z, a.iterator());
    }

    /**
     * Left folding of collection.
     * @param f function to fold with
     * @param z initial value
     * @param a collection to fold
     * @param <T> type of elements
     * @param <U> type of return value
     * @return value of folded collection
     */
    public static @NotNull <T, U> U foldl(@NotNull Function2<? super U, ? super T, ? extends U> f,
                                          U z,
                                          @NotNull Collection<T> a) {
        return foldlByIterator(f, z, a.iterator());
    }

    private static @NotNull <T, U> U foldrByIterator(@NotNull Function2<? super T, ? super U, ? extends U> f,
                                                     U z,
                                                     @NotNull Iterator<T> it) {
        if (it.hasNext()) {
            return f.apply(it.next(), foldrByIterator(f, z, it));
        }

        return z;
    }

    private static @NotNull <T, U> U foldlByIterator(@NotNull Function2<? super U, ? super T, ? extends U> f,
                                                     U z,
                                                     @NotNull Iterator<T> it) {
        if (it.hasNext()) {
            return foldlByIterator(f, f.apply(z, it.next()), it);
        }

        return z;
    }
}
