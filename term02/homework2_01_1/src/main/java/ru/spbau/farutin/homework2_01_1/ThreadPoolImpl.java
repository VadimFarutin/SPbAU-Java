package ru.spbau.farutin.homework2_01_1;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ThreadPoolImpl - represents thread pool with fixed number of threads.
 * @param <T> type of stored tasks return values
 */
public class ThreadPoolImpl<T> {
    private final Queue<LightFuture<T>> tasks = new ArrayDeque<>();
    private Thread[] threads;

    /**
     * Constructor for ThreadPoolImpl.
     * @param n threads number
     */
    public ThreadPoolImpl(int n) {
        Runnable executor = new LightFuturePoolExecutor();
        threads = new Thread[n];

        for (int i = 0; i < n; i++) {
            threads[i] = new Thread(executor);
            threads[i].start();
        }
    }

    /**
     * Adds new task, which will be executed when there will be a free thread.
     * @param task new task to execute
     * @return LightFuture representing added task
     */
    public @NotNull LightFuture<T> addTask(@NotNull Supplier<T> task) {
        LightFuture<T> newTask = new LightFutureImpl(task);

        synchronized (tasks) {
            tasks.add(newTask);
            tasks.notify();
        }

        return newTask;
    }

    /**
     * Interrupts all threads.
     */
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * LightFuturePoolExecutor - Runnable implementation,
     * waits for new task and executes it, works until will be interrupted.
     */
    private class LightFuturePoolExecutor implements Runnable {
        @Override
        public void run() {
            LightFuture<T> task;

            try {
                while (!Thread.interrupted()) {
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }

                        task = tasks.remove();
                        tasks.notify();
                    }

                    try {
                        task.get();
                    } catch (LightExecutionException ignored) {
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    /**
     * LightFutureImpl - LightFuture implementation for ThreadPoolImpl.
     */
    private class LightFutureImpl implements LightFuture<T> {
        private Supplier<T> task;
        private volatile T value = null;
        private boolean ready = false;
        private boolean success = false;

        public LightFutureImpl(@NotNull Supplier<T> task) {
            this.task = task;
        }

        @Override
        public boolean isReady() {
            return ready;
        }

        @Override
        public @Nullable T get() throws LightExecutionException {
            if (!ready) {
                synchronized (this) {
                    if (!ready) {
                        try {
                            value = task.get();
                        } catch (Exception e) {
                            throw new LightExecutionException();
                        } finally {
                            ready = true;
                        }

                        success = true;
                    }
                }
            }

            if (!success) {
                throw new LightExecutionException();
            }

            return value;
        }

        @Override
        public @NotNull LightFuture<T> thenApply(@NotNull Function<T, T> function) {
            return addTask(() -> {
                try {
                    return function.apply(get());
                } catch (LightExecutionException e) {
                    throw new RuntimeException("composition failed");
                }
            });
        }
    }
}
