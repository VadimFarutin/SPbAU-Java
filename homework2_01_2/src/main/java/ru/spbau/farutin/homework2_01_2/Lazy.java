package ru.spbau.farutin.homework2_01_2;

/**
 * Lazy - lazy Supplier equivalent.
 * @param <T> type of return value
 */
public interface Lazy<T> {
    /**
     * Starts Supplier computation, but only at first call.
     * Returns same value each next time.
     * @return execution result
     */
    T get();
}
