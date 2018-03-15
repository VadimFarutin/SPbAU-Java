package ru.spbau.farutin.homework05_2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * SetTest - unit tests for Set.
 */
public class SetTest {
    /**
     * Tests size() on empty set.
     */
    @Test
    public void testSizeEmpty() {
        Set<Integer> set = new Set<>();

        assertEquals("set should be empty", 0, set.size());
    }

    /**
     * Tests size() on not empty set.
     */
    @Test
    public void testSizeNotEmpty() {
        Set<Integer> set = new Set<>();
        set.add(1);
        set.add(2);
        set.add(3);

        assertEquals("wrong size", 3, set.size());
    }

    /**
     * Tests contains() on set which contains given value.
     */
    @Test
    public void testSetContainsTrue() {
        Set<String> set = new Set<>();
        set.add("first");
        set.add("second");
        set.add("third");

        assertTrue("missed value", set.contains("second"));
    }

    /**
     * Tests contains() on set which does not contain given value.
     */
    @Test
    public void testSetContainsFalse() {
        Set<String> set = new Set<>();
        set.add("first");
        set.add("second");
        set.add("third");

        assertFalse("should not be in set", set.contains("fourth"));
    }

    /**
     * Tests add() on set which does not contain given value.
     */
    @Test
    public void testAddNotContained() {
        Set<Double> set = new Set<>();

        assertTrue("should not be in set", set.add(1.0));
        assertTrue("missed value", set.contains(1.0));
    }

    /**
     * Tests add() on set which contains given value.
     */
    @Test
    public void testAddContained() {
        Set<Double> set = new Set<>();

        assertTrue("should not be in set", set.add(1.0));
        assertFalse("missed value", set.add(1.0));
        assertTrue("missed value", set.contains(1.0));
    }
}