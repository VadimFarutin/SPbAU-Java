package ru.spbau.farutin.homework01.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ListTest.java - unit tests for List class.
 */
public class ListTest extends List {
    /**
     * Tests add() on empty list.
     */
    @Test
    public void testAddEmpty() {
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(key, value);

        assertTrue(list.find(key) != null, "missed key");
        assertTrue(list.find(key).equals(value), "wrong value");
    }

    /**
     * Tests add() on not empty list.
     */
    @Test
    public void testAddNotEmpty() {
        String oldHeadKey = "head key";
        String oldHeadValue = "head value";
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(oldHeadKey, oldHeadValue);
        list.add(key, value);

        assertTrue(list.find(oldHeadKey) != null, "missed key");
        assertTrue(list.find(oldHeadKey).equals(oldHeadValue), "wrong value");
        assertTrue(list.find(key) != null, "missed key");
        assertTrue(list.find(key).equals(value), "wrong value");
    }

    /**
     * Tests find() on list, which has given key.
     */
    @Test
    public void testFindIsIn() {
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(key, value);

        assertEquals(value, list.find(key), "wrong value");
    }

    /**
     * Tests find() on list, which has not given key.
     */
    @Test
    public void testFindIsNotIn() {
        List list = new List();
        list.add("tested key", "tested value");

        assertEquals(null, list.find("this key is not there"), "wrong value");
    }

    /**
     * Tests remove() on list, which has given key.
     */
    @Test
    public void testRemoveWasIn() {
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(key, value);

        assertEquals(value, list.remove(key), "wrong value");
        assertEquals(null, list.getHead(), "wrong head");
    }

    /**
     * Tests remove() on list, which has not given key.
     */
    @Test
    public void testRemoveWasNotIn() {
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(key, value);

        assertEquals(null, list.remove("this key is not there"), "wrong value");

        assertTrue(list.find(key) != null, "missed key");
        assertTrue(list.find(key).equals(value), "wrong value");
    }

    /**
     * Tests clear() on empty list.
     */
    @Test
    public void testClearEmpty() {
        List list = new List();
        list.clear();
        assertEquals(null, list.getHead(), "wrong head");
    }

    /**
     * Tests clear() on not empty list.
     */
    @Test
    public void testClearNotEmpty() {
        List list = new List();
        list.add("tested key", "tested value");
        list.clear();
        assertEquals(null, list.getHead(), "wrong head");
    }
}