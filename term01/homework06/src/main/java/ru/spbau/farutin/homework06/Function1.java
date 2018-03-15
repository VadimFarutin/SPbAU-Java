package ru.spbau.farutin.homework06;

import org.jetbrains.annotations.NotNull;

/**
 * Function1 - interface for function of one argument.
 * @param <T> type of argument
 * @param <U> type of return value
 */
public interface Function1<T, U> {
    /**
     * Calculates function with given argument.
     * @param arg argument to apply
     * @return function value
     */
    U apply(T arg);

    /**
     * Composition of two functions.
     * @param g function to compose with
     * @param <S> type of return value of composition
     * @return composition, this function before g
     */
    default @NotNull <S> Function1<T, S> compose(@NotNull Function1<? super U, ? extends S> g) {
        return t -> g.apply(apply(t));
    }
}
