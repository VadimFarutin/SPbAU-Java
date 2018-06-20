package ru.spbau.farutin.test2_04;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Tests for CountdownLatch.
 */
public class CountdownLatchTest {
    private static final int MILLIS_TO_SLEEP = 500;

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorWithNegativeValue() throws Exception {
        CountdownLatch latch = new CountdownLatch(-5);
    }

    @Test
    public void testAwaitReallyBlocks() throws Exception {
        CountdownLatch latch = new CountdownLatch(1);
        final boolean[] blocked = {true};

        Thread thread = new Thread(() -> {
            try {
                latch.await();
                blocked[0] = false;
            } catch (InterruptedException ignored) {
            }
        });

        thread.start();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);
        thread.interrupt();

        assertTrue(blocked[0]);
    }

    @Test
    public void testCountDownOpened() throws Exception {
        CountdownLatch latch = new CountdownLatch(1);
        final boolean[] opened = {false, false};

        Thread thread1 = new Thread(() -> {
            try {
                latch.await();
                opened[0] = true;
            } catch (InterruptedException ignored) {
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                latch.await();
                opened[1] = true;
            } catch (InterruptedException ignored) {
            }
        });

        thread1.start();
        thread2.start();

        latch.countDown();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);

        thread1.interrupt();
        thread2.interrupt();

        assertTrue(opened[0]);
        assertTrue(opened[1]);
    }

    @Test
    public void testCountDownClosed() throws Exception {
        CountdownLatch latch = new CountdownLatch(2);
        final boolean[] opened = {false, false};

        Thread thread1 = new Thread(() -> {
            try {
                latch.await();
                opened[0] = true;
            } catch (InterruptedException ignored) {
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                latch.await();
                opened[1] = true;
            } catch (InterruptedException ignored) {
            }
        });

        thread1.start();
        thread2.start();

        latch.countDown();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);

        thread1.interrupt();
        thread2.interrupt();

        assertFalse(opened[0]);
        assertFalse(opened[1]);
    }

    @Test
    public void testCountDownWaits() throws Exception {
        CountdownLatch latch = new CountdownLatch(0);
        final boolean[] blocked = {true};

        Thread thread = new Thread(() -> {
            try {
                latch.countDown();
                blocked[0] = false;
            } catch (InterruptedException ignored) {
            }
        });

        thread.start();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);
        thread.interrupt();

        assertTrue(blocked[0]);
    }

    @Test
    public void testCountUp() throws Exception {
        CountdownLatch latch = new CountdownLatch(0);
        final boolean[] opened = {false};

        Thread thread = new Thread(() -> {
            try {
                latch.countDown();
                opened[0] = true;
            } catch (InterruptedException ignored) {
            }
        });

        thread.start();
        latch.countUp();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);
        thread.interrupt();

        assertTrue(opened[0]);
    }

    @Test
    public void testMultipleCountDownWaiters() throws Exception {
        CountdownLatch latch = new CountdownLatch(0);
        final boolean[] opened = {false, false};

        Thread thread1 = new Thread(() -> {
            try {
                latch.countDown();
                opened[0] = true;
            } catch (InterruptedException ignored) {
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                latch.countDown();
                opened[1] = true;
            } catch (InterruptedException ignored) {
            }
        });

        thread1.start();
        thread2.start();

        latch.countUp();
        TimeUnit.MILLISECONDS.sleep(MILLIS_TO_SLEEP);

        thread1.interrupt();
        thread2.interrupt();

        // only one has passed
        assertTrue(opened[0] ^ opened[1]);
    }
}
