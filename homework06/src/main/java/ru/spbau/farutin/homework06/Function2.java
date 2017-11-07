package ru.spbau.farutin.homework06;

import org.jetbrains.annotations.NotNull;

/**
 * Function2 - interface for function of two arguments.
 * @param <T> type of first argument
 * @param <U> type of second argument
 * @param <S> type of return value
 */
public interface Function2<T, U, S> {
    /**
     * Calculates function with given arguments.
     * @param arg1 first argument to apply
     * @param arg2 second argument to apply
     * @return function value
     */
    S apply(T arg1, U arg2);

    /**
     * Composition of two functions.
     * @param g function to compose with
     * @param <R> type of return value of composition
     * @return composition, this function before g
     */
    default @NotNull <R> Function2<T, U, R> compose(@NotNull Function1<? super S, ? extends R> g) {
        return (t, u) -> g.apply(apply(t, u));
    }

    /**
     * Binds first argument.
     * @param arg value to bind with
     * @return binded function
     */
    default @NotNull Function1<U, S> bind1(T arg) {
        return u -> apply(arg, u);
    }

    /**
     * Binds second argument.
     * @param arg value to bind with
     * @return binded function
     */
    default @NotNull Function1<T, S> bind2(U arg) {
        return t -> apply(t, arg);
    }

    /**
     * Represents currying.
     * @return curried function.
     */
    default @NotNull Function1<T, Function1<U, S>> curry() {
        return t -> (u -> apply(t, u));
    }
}
