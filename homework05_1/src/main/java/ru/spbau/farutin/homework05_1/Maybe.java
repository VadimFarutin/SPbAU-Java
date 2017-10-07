package ru.spbau.farutin.homework05_1;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Maybe.java - generic class with null or not null value.
 */
public class Maybe<T> {
    private @Nullable T t = null;

    private Maybe(@Nullable T t) {
        this.t = t;
    }

    /**
     * Generates Maybe with not null value
     * @param t new value
     * @return instance with new value
     */
    public static @NotNull <T> Maybe<T> just(@NotNull T t) {
        return new Maybe<T>(t);
    }

    /**
     * Generates Maybe with null value
     * @return instance with null value
     */
    public static @NotNull <T> Maybe<T> nothing() {
        return new Maybe<T>(null);
    }

    /**
     * Getter for stored value, throws exception if it is null
     * @return not null value
     */
    public @NotNull T get() throws MaybeException {
        if (!isPresent()) {
            throw new MaybeException("Get from nothing");
        }

        return t;
    }

    /**
     * Checks whether stored value is null or not
     * @return true if value is not null, false otherwise
     */
    public boolean isPresent() {
        return t != null;
    }

    /**
     * Applies mapper to stored value
     * @param mapper function to apply
     * @return instance with new value
     */
    public @NotNull <U> Maybe<U> map(@NotNull Function<T, U> mapper) {
        if (!isPresent()) {
            return new Maybe<U>(null);
        }

        return new Maybe<U>(mapper.apply(t));
    }
}
