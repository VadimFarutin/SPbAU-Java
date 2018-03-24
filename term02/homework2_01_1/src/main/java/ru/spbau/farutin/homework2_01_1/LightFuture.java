package ru.spbau.farutin.homework2_01_1;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * LightFuture - interface for tasks added in Thread pool.
 * @param <T> - return type
 */
public interface LightFuture<T> {
    /**
     * Tells whether task result is ready or not.
     * @return true if task is ready, false otherwise
     */
    boolean isReady();

    /**
     * Returns task result, but only after it is ready.
     * @return task result
     * @throws LightExecutionException if execution causes exception
     */
    T get() throws LightExecutionException;

    /**
     * Adds in Thread pool new task, based on this task result.
     * Won't be executed before its parent.
     * @param function new task, takes this task result as input
     * @return LightFuture with new task
     */
    @NotNull LightFuture<T> thenApply(@NotNull Function<T, T> function);
}
