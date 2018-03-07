package ru.spbau.farutin.homework2_01_2;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * LazyFactoryTest - tests for LazyFactory.
 */
public class LazyFactoryTest {
    @Test
    public void testSingleThreadLazySimple() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSingleThreadLazy(() -> 42);

        assertThat(lazy.get(), is(42));
    }

    @Test
    public void testSingleThreadLazyWithNullValue() throws Exception {
        Lazy<Object> lazy = LazyFactory.createSingleThreadLazy(() -> null);

        assertThat(lazy.get() == null, is(true));
    }

    @Test
    public void testSingleThreadLazyIsReallyLazy() throws Exception {
        ArrayList<String> output = new ArrayList<>();

        Lazy<Boolean> lazy = LazyFactory.createSingleThreadLazy(() -> {
            output.add("second");
            return true;
        });

        output.add("first");

        assertThat(lazy.get(), is(true));
        assertThat(output.size(), is(2));
        assertThat(output.get(0), is("first"));
        assertThat(output.get(1), is("second"));
    }

    @Test
    public void testSingleThreadLazyIsExecutingOnlyOnce() throws Exception {
        ArrayList<String> output = new ArrayList<>();

        Lazy<Boolean> lazy = LazyFactory.createSingleThreadLazy(() -> {
            output.add("hello");
            return true;
        });

        for (int i = 0; i < 10; i++) {
            assertThat(lazy.get(), is(true));
        }

        assertThat(output.size(), is(1));
        assertThat(output.get(0), is("hello"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSingleThreadLazyWithNullSupplier() throws Exception {
        LazyFactory.createSingleThreadLazy(null);
    }

    @Test
    public void createMultiThreadLazySimple() throws Exception {
        final boolean[] failed = {false};

        Lazy<Integer> lazy = LazyFactory.createMultiThreadLazy(() -> 42);
        Runnable check = () -> failed[0] |= (lazy.get() != 42);

        Thread first = new Thread(check);
        Thread second = new Thread(check);

        first.start();
        second.start();

        first.join();
        second.join();

        assertFalse(failed[0]);
    }

    @Test
    public void createMultiThreadLazyWithNullValue() throws Exception {
        final boolean[] failed = {false};

        Lazy<Object> lazy = LazyFactory.createMultiThreadLazy(() -> null);
        Runnable check = () -> failed[0] |= (lazy.get() != null);

        Thread first = new Thread(check);
        Thread second = new Thread(check);
        Thread third = new Thread(check);

        first.start();
        second.start();
        third.start();

        first.join();
        second.join();
        third.join();

        assertFalse(failed[0]);
    }

    @Test
    public void createMultiThreadLazyIsReallyLazy() throws Exception {
        ArrayList<String> output = new ArrayList<>();

        Lazy<Boolean> lazy = LazyFactory.createMultiThreadLazy(() -> {
            output.add("second");
            return true;
        });
        Runnable check = lazy::get;

        output.add("first");

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(check));
            threads.get(i).start();
        }

        for (int i = 0; i < 4; i++) {
            threads.get(i).join();
        }

        assertThat(output.size(), is(2));
        assertThat(output.get(0), is("first"));
        assertThat(output.get(1), is("second"));
    }

    @Test
    public void createMultiThreadLazyIsExecutingOnlyOnce() throws Exception {
        ArrayList<String> output = new ArrayList<>();

        Lazy<Boolean> lazy = LazyFactory.createMultiThreadLazy(() -> {
            output.add("hello");
            return true;
        });
        Runnable check = () ->  {
            for (int i = 0; i < 1000; i++) {
                lazy.get();
            }
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(check));
            threads.get(i).start();
        }

        for (int i = 0; i < 4; i++) {
            threads.get(i).join();
        }

        assertThat(output.size(), is(1));
        assertThat(output.get(0), is("hello"));
    }

    @Test
    public void createMultiThreadLazyRace() throws Exception {
        ArrayList<String> output = new ArrayList<>();

        Lazy<Boolean> lazy = LazyFactory.createMultiThreadLazy(() -> {
            output.add("hello");
            return true;
        });
        Runnable check = () ->  {
            for (int i = 0; i < 1e7; i++) {
                lazy.get();
            }
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            threads.add(new Thread(check));
            threads.get(i).start();
        }

        for (int i = 0; i < 8; i++) {
            threads.get(i).join();
        }

        assertThat(output.size(), is(1));
        assertThat(output.get(0), is("hello"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMultiThreadLazyWithNullSupplier() throws Exception {
        LazyFactory.createMultiThreadLazy(null);
    }
}
