package ru.spbau.farutin.test2_04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CountdownLatch implementation which allows to increase counter.
 */
public class CountdownLatch {
    private final Lock lock = new ReentrantLock();
    private final Condition zeroCount = lock.newCondition();
    private final Condition notZeroCount = lock.newCondition();
    private int count;

    /**
     * Constructs a CountdownLatch initialized with the given count.
     * @param count non negative initial value of counter
     * which threads will wait to be zero
     */
    public CountdownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Negative count value!");
        }

        this.count = count;
    }

    /**
     * Blocks thread until counter will become zero.
     * @throws InterruptedException if thread was interrupted while waiting
     */
    public void await() throws InterruptedException {
        lock.lock();

        try {
            while (count > 0) {
                zeroCount.await();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Blocks thread until counter will become not zero.
     * Then decreases counter and starts all threads waiting for the counter
     * if it has become zero.
     * @throws InterruptedException if thread was interrupted while waiting
     */
    public void countDown() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) {
                notZeroCount.await();
            }

            count--;

            if (count == 0) {
                zeroCount.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Increases counter and starts all threads waiting in countDown()
     * if it was zero before increasing.
     */
    public void countUp() {
        lock.lock();

        count++;

        if (count == 1) {
            notZeroCount.signalAll();
        }

        lock.unlock();
    }
}
