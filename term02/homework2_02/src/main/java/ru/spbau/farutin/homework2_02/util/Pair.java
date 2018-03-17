package ru.spbau.farutin.homework2_02.util;

/**
 * Pair - simple pair implementation.
 * @param <T> first value type parameter
 * @param <S> second value type parameter
 */
public class Pair<T, S> {
    private T first;
    private S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public T first() {
        return first;
    }

    public S second() {
        return second;
    }
}
