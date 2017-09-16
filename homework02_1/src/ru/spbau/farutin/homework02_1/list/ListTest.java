package ru.spbau.farutin.homework02_1.list;

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

        assertEquals(key, list.head.key, "wrong head key");
        assertEquals(value, list.head.value, "wrong head value");
        assertEquals(null, list.head.next, "wrong head next");
        assertEquals(null, list.head.prev, "wrong head prev");
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

        assertEquals(key, list.head.key, "wrong head key");
        assertEquals(value, list.head.value, "wrong head value");
        assertEquals(oldHeadKey, list.head.next.key, "wrong head key");
        assertEquals(oldHeadValue, list.head.next.value, "wrong head value");
        assertEquals(null, list.head.next.next, "wrong head next");
        assertEquals(null, list.head.next.prev, "wrong head prev");
        assertEquals(null, list.head.prev, "wrong head prev");
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
        assertEquals(key, list.getHead().key, "wrong head key");
        assertEquals(value, list.getHead().value, "wrong head value");
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

    /**
     * Tests getHead() on empty list.
     */
    @Test
    public void testGetHeadEmpty() {
        List list = new List();
        assertEquals(null, list.getHead(), "wrong head");
    }

    /**
     * Tests getHead() on not empty list.
     */
    @Test
    public void testGetHeadNotEmpty() {
        String key = "tested key";
        String value = "tested value";

        List list = new List();
        list.add(key, value);
        List.Node node = list.getHead();

        assertEquals(key, node.key, "wrong head key");
        assertEquals(value, node.value, "wrong head value");
        assertEquals(null, node.next, "wrong head next");
        assertEquals(null, node.prev, "wrong head prev");
    }
}