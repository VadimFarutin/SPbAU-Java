package ru.spbau.farutin.homework2_01_2;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * LazyFactory - provides methods to build Lazy objects
 * to work with one or multiple threads.
 */
public class LazyFactory {
    /**
     * Builds Lazy value for one thread.
     * @param supplier supplier to execute
     * @param <T> supplier return value type
     * @return lazy equivalent of given supplier
     * @throws IllegalArgumentException if supplier is null
     */
    public static @NotNull <T> Lazy<T> createSingleThreadLazy(@Nullable Supplier<T> supplier)
            throws IllegalArgumentException {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier is null!");
        }

        return new Lazy<T>() {
            private T value = null;
            private boolean executed = false;

            @Override
            public T get() {
                if (!executed) {
                    value = supplier.get();
                    executed = true;
                }

                return value;
            }
        };
    }

    /**
     * Builds Lazy value for multiple threads.
     * @param supplier supplier to execute
     * @param <T> supplier return value type
     * @return lazy equivalent of given supplier
     * @throws IllegalArgumentException if supplier is null
     */
    public static @NotNull <T> Lazy<T> createMultiThreadLazy(@Nullable Supplier<T> supplier)
            throws IllegalArgumentException {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier is null!");
        }

        return new Lazy<T>() {
            private volatile T value = null;
            private boolean executed = false;

            @Override
            public T get() {
                if (!executed) {
                    synchronized (this) {
                        if (!executed) {
                            value = supplier.get();
                            executed = true;
                        }
                    }
                }

                return value;
            }
        };
    }
}
