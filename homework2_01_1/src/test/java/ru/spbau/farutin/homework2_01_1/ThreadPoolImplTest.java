package ru.spbau.farutin.homework2_01_1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * ThreadPoolImplTest - tests for ThreadPoolImpl.
 */
public class ThreadPoolImplTest {
    @Test
    public void testPoolWithoutTasks() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        pool.shutdown();
    }

    @Test
    public void testPoolNotFull() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);

        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 2);
        LightFuture<Integer> task2 = pool.addTask(() -> 42);

        assertThat(task1.get(), is(4));
        assertThat(task2.get(), is(42));

        pool.shutdown();
    }

    @Test
    public void testPoolFull() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);

        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 2);
        LightFuture<Integer> task2 = pool.addTask(() -> 42);
        LightFuture<Integer> task3 = pool.addTask(() -> {
            int f = 1;

            for (int i = 1; i <= 10000; i++) {
                f = (f * i) % 1000007;
            }

            return f;
        });
        LightFuture<Integer> task4 = pool.addTask(() -> 17 + 100);
        LightFuture<Integer> task5 = pool.addTask(() -> 111 * 2 * 3);
        LightFuture<Integer> task6 = pool.addTask(() -> 31415926);

        assertThat(task1.get(), is(4));
        assertThat(task2.get(), is(42));
        assertThat(task3.get(), is(879374));
        assertThat(task4.get(), is(117));
        assertThat(task5.get(), is(666));
        assertThat(task6.get(), is(31415926));

        pool.shutdown();
    }

    /**
     * Checks that threads stop execution after shutdown() call.
     */
    @Test
    public void testPoolShutdown() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        final boolean[] finished = {false};
        LightFuture<Integer> task1 = pool.addTask(() -> {
            int f = 1;

            for (int i = 1; i <= 10000000; i++) {
                f = (f * i) % 1000007;
            }

            finished[0] = true;
            return f;
        });

        pool.shutdown();
        assertFalse(finished[0]);
    }

    /**
     * Tests ThreadPoolImpl with many threads and many tasks.
     */
    @Test
    public void testPoolRaceCondition() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(20);
        Supplier<Integer> task = () -> 42;
        Function<Integer, Integer> after = (i) -> 0;

        for (int i = 0; i < 10000; i++) {
            LightFuture<Integer> result = pool.addTask(task);
            result.thenApply(after);
        }

        pool.shutdown();
    }

    @Test
    public void testLightFutureReady() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 2);

        Thread.sleep(500);
        assertTrue(task1.isReady());

        pool.shutdown();
    }

    @Test
    public void testLightFutureNotReady() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task1 = pool.addTask(() -> {
            int f = 1;

            for (int i = 1; i <= 1e9; i++) {
                f = (f * i) % 1000007;
            }

            return f;
        });

        assertFalse(task1.isReady());

        pool.shutdown();
    }

    @Test
    public void testLightFutureReadyAfterGetCall() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task1 = pool.addTask(() -> {
            int f = 1;

            for (int i = 1; i <= 10000; i++) {
                f = (f * i) % 1000007;
            }

            return f;
        });

        assertThat(task1.get(), is(879374));
        assertTrue(task1.isReady());

        pool.shutdown();
    }

    @Test(expected = LightExecutionException.class)
    public void testLightFutureWithException() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        LightFuture<Integer> task1 = pool.addTask(() -> {
            throw new RuntimeException();
        });

        task1.get();

        pool.shutdown();
    }

    /**
     * Checks that execution of LightFuture happens only once,
     * even after several get() calls.
     */
    @Test
    public void testLightFutureExecutesOnlyOnce() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        final int[] cnt = {0};
        LightFuture<Integer> task1 = pool.addTask(() -> cnt[0]++);

        assertThat(task1.get(), is(0));

        for (int i = 0; i < 10; i++) {
            task1.get();
        }

        assertThat(cnt[0], is(1));

        pool.shutdown();
    }

    /**
     * Checks that new task from thenApply() has correct result.
     */
    @Test
    public void testLightFutureThenApply() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);

        LightFuture<Integer> task1 = pool.addTask(() -> 2 * 3);
        LightFuture<Integer> task2 = task1.thenApply((i) -> i + 1);
        LightFuture<Integer> task3 = task1.thenApply((i) -> i + 2);

        assertThat(task1.get(), is(6));
        assertThat(task2.get(), is(7));
        assertThat(task3.get(), is(8));

        pool.shutdown();
    }

    /**
     * Checks that new task from thenApply() executes after original one.
     */
    @Test
    public void testLightFutureThenApplyTaskExecutesAfter() throws Exception {
        ThreadPoolImpl<Integer> pool = new ThreadPoolImpl<>(4);
        final ArrayList<String> output = new ArrayList<>();

        LightFuture<Integer> task1 = pool.addTask(() -> {
            output.add("first");
            int f = 1;
            for (int i = 1; i <= 10000; i++) {
                f = (f * i) % 1000007;
            }

            return f;
        });
        LightFuture<Integer> task2 = task1.thenApply((i) -> {
            output.add("second");
            return 42;
        });
        LightFuture<Integer> task3 = task2.thenApply((i) -> {
            output.add("third");
            return 2 * 2;
        });

        task1.get();
        task2.get();
        task3.get();

        assertThat(output.size(), is(3));
        assertThat(output.get(0), is("first"));
        assertThat(output.get(1), is("second"));
        assertThat(output.get(2), is("third"));

        pool.shutdown();
    }
}
