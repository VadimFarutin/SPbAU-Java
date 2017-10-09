package ru.spbau.farutin.homework01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * HashTableTest.java - unit tests for HashTable class.
 */
public class HashTableTest extends HashTable {
    /**
     * Tests size() on empty hashtable.
     */
    @Test
    public void testSizeEmpty() {
        HashTable hashTable = new HashTable();
        assertEquals(0, hashTable.size(), "wrong size");
    }

    /**
     * Tests size() after adding values with different keys.
     */
    @Test
    public void testSizeNotEmpty() {
        HashTable hashTable = new HashTable();
        hashTable.put("first", "Hello");
        hashTable.put("second", "World");
        hashTable.put("third", "!");

        assertEquals(3, hashTable.size(), "wrong size");
    }

    /**
     * Tests size() after adding values with same keys.
     */
    @Test
    public void testSizeNotEmptySameKeys() {
        HashTable hashTable = new HashTable();
        String sameKey = "second";
        hashTable.put("first", "Hello");
        hashTable.put(sameKey, "World");
        hashTable.put(sameKey, "this value will replace old one");

        assertEquals(2, hashTable.size(), "wrong size");
    }

    /**
     * Tests contains() on hashtable, which has given key.
     */
    @Test
    public void testContainsIsIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(true, hashTable.contains(key), "key must be in hashtable");
    }

    /**
     * Tests contains() on hashtable, which has not given key.
     */
    @Test
    public void testContainsIsNotIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(false, hashTable.contains("this key is not there"), "key must be not in hashtable");
    }

    /**
     * Tests get() on hashtable, which has given key.
     */
    @Test
    public void testGetIsIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(value, hashTable.get(key), "wrong value");
    }

    /**
     * Tests get() on hashtable, which has not given key.
     */
    @Test
    public void testGetIsNotIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(null, hashTable.get("this key is not there"), "wrong value");
    }

    /**
     * Tests put() on empty hashtable.
     */
    @Test
    public void testPutEmpty() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(value, hashTable.get(key), "wrong value");
    }

    /**
     * Tests put() on hashtable, which already has given key.
     */
    @Test
    public void testPutNotEmpty() {
        String key = "tested key";
        String value = "tested value";
        String newValue = "new value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);
        hashTable.put(key, newValue);

        assertEquals(newValue, hashTable.data[0].find(key), "wrong value");
    }

    /**
     * Tests remove() on hashtable, which has given key.
     */
    @Test
    public void testRemoveWasIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(value, hashTable.remove(key), "wrong value");
        assertEquals(0, hashTable.size(), "wrong size");
    }

    /**
     * Tests remove() on hashtable, which has not given key.
     */
    @Test
    public void testRemoveWasNotIn() {
        String key = "tested key";
        String value = "tested value";

        HashTable hashTable = new HashTable();
        hashTable.put(key, value);

        assertEquals(null, hashTable.remove("this key is not in hashtable"), "wrong value");
        assertEquals(1, hashTable.size(), "wrong size");
    }

    /**
     * Tests clear() on empty hashtable.
     */
    @Test
    public void testClearEmpty() {
        HashTable hashTable = new HashTable();
        hashTable.clear();

        assertEquals(0, hashTable.size(), "wrong size");
    }

    /**
     * Tests clear() on hashtable with some keys.
     */
    @Test
    public void testClearNotEmpty() {
        HashTable hashTable = new HashTable();
        hashTable.put("first", "Hello");
        hashTable.put("second", "World");
        hashTable.put("third", "!");
        hashTable.clear();

        assertEquals(0, hashTable.size(), "wrong size");
    }

    /**
     * Tests resize() adding first key.
     */
    @Test
    public void testResizeFirst() {
        HashTable hashTable = new HashTable();
        hashTable.put("first", "this will increase size from 1 to 2");

        assertEquals(1, hashTable.size(), "wrong size");
    }

    /**
     * Tests resize() with many elements.
     */
    @Test
    public void testResizeMany() {
        HashTable hashTable = new HashTable();
        hashTable.put("first", "this will increase size from 1 to 2");
        hashTable.put("second", "this will increase size from 2 to 4");

        assertEquals(true, hashTable.contains("first"), "missed first key");
        assertEquals(true, hashTable.contains("second"), "missed second key");
        assertEquals(2, hashTable.size(), "wrong size");

        hashTable.put("third", "nothing happens");
        hashTable.put("fourth", "this will increase size from 4 to 8");

        assertEquals(true, hashTable.contains("first"), "missed first key");
        assertEquals(true, hashTable.contains("second"), "missed second key");
        assertEquals(true, hashTable.contains("third"), "missed third key");
        assertEquals(true, hashTable.contains("fourth"), "missed fourth key");
        assertEquals(4, hashTable.size(), "wrong size");
    }

    /**
     * Tests behavior when collision between two keys happens.
     */
    @Test
    public void testCollisionTwoKeys() {
        HashTable hashTable = new HashTable();
        hashTable.put("first", "hash is equals to (97440432 % hashtable.data.length)");
        hashTable.put("second", "hash is equals to (-906279820 % hashtable.data.length),");
        // h1 == h2 when all elements are added

        assertEquals(true, hashTable.contains("first"), "missed first key");
        assertEquals(true, hashTable.contains("second"), "missed second key");
        assertEquals(2, hashTable.size(), "wrong size");
    }

    /**
     * Tests behavior when collision between many keys happens.
     */
    @Test
    public void testCollisionManyKeys() {
        HashTable hashTable = new HashTable();
        hashTable.put("first key", "hash is equals to (97440432 % hashtable.data.length)");
        hashTable.put("second", "hash is equals to ((-906279820) % hashtable.data.length)");
        hashTable.put("third", "hash is equals to (110331239 % hashtable.data.length)");
        hashTable.put("fourth", "hash is equals to ((-1268684262) % hashtable.data.length)");
        hashTable.put("fifth", "hash is equals to (97428919 % hashtable.data.length)");
        hashTable.put("sixth", "hash is equals to (109451990 % hashtable.data.length)");
        // h1 == h3 && h3 == h5 when all elements are added

        assertEquals(true, hashTable.contains("first key"), "missed first key");
        assertEquals(true, hashTable.contains("second"), "missed second key");
        assertEquals(true, hashTable.contains("third"), "missed third key");
        assertEquals(true, hashTable.contains("fourth"), "missed fourth key");
        assertEquals(true, hashTable.contains("fifth"), "missed fifth key");
        assertEquals(true, hashTable.contains("sixth"), "missed sixth key");
        assertEquals(6, hashTable.size(), "wrong size");
    }
}