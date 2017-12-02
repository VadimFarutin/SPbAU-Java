package ru.spbau.farutin.homework07;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * SetTest - tests for Set class.
 */
public class SetTest {
    /**
     * Tests iterator of an empty set.
     */
    @Test
    public void testIteratorEmpty() {
        Iterator<Integer> it = new Set<Integer>().iterator();

        assertFalse("set should be empty", it.hasNext());
    }

    /**
     * Tests iterator of a not empty set.
     */
    @Test
    public void testIteratorNotEmpty() {
        Set<Integer> set = new Set<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        Iterator<Integer> it = set.iterator();

        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 1, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 2, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 3, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 4, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 5, it.next().intValue());

        assertFalse("there should not be any more values", it.hasNext());
    }

    /**
     * Calls next() from iterator after taking last element.
     */
    @Test(expected = NoSuchElementException.class)
    public void testIteratorNextWithException() {
        Set<Integer> set = new Set<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        Iterator<Integer> it = set.iterator();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
        it.next();
    }

    /**
     * Calls remove() which is not supported.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        new Set<Integer>().iterator().remove();
    }

    /**
     * Tests size() on an empty set.
     */
    @Test
    public void testSizeEmpty() {
        assertEquals("set should be empty", 0, new Set<Integer>().size());
    }

    /**
     * Tests size() on a not empty set.
     */
    @Test
    public void testSizeNotEmpty() {
        Set<Integer> set = new Set<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertEquals("wrong size", 5, set.size());
    }

    /**
     * Tests add() on set which does not contain given value.
     */
    @Test
    public void testAddNotContained() {
        Set<Integer> set = new Set<>();

        assertTrue("should not be in set", set.add(1));
        assertTrue("missed value", set.contains(1));
    }

    /**
     * Tests add() on set which contains given value.
     */
    @Test
    public void testAddContained() {
        Set<Integer> set = new Set<>();

        assertTrue("should not be in set", set.add(1));
        assertFalse("missed value", set.add(1));
        assertTrue("missed value", set.contains(1));
    }
}
