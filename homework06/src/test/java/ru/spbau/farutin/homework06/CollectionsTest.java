package ru.spbau.farutin.homework06;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * CollectionsTest - tests for Collections.
 */
public class CollectionsTest {
    /**
     * Tests map on ArrayList with integers.
     */
    @Test
    public void testMap() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            expected.add(i * i);
        }

        List<Integer> res = Collections.map(x -> x * x, list);

        assertEquals("wrong array size", expected.size(), res.size());
        assertArrayEquals("wrong array values", expected.toArray(), res.toArray());
    }

    /**
     * Tests filter on ArrayList with integers.
     */
    @Test
    public void testFilter() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 10; i += 2) {
            list.add(i);
            list.add(i + 1);
            expected.add(i);
        }

        List<Integer> res = Collections.filter(x -> x % 2 == 0, list);

        assertEquals("wrong array size", expected.size(), res.size());
        assertArrayEquals("wrong array values", expected.toArray(), res.toArray());
    }

    /**
     * Tests takeWhile on ArrayList with integers.
     */
    @Test
    public void testTakeWhile() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            expected.add(i);
        }

        List<Integer> res = Collections.takeWhile(x -> x < 7, list);

        assertEquals("wrong array size", expected.size(), res.size());
        assertArrayEquals("wrong array values", expected.toArray(), res.toArray());
    }

    /**
     * Tests takeUnless on ArrayList with integers.
     */
    @Test
    public void testTakeUnless() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            expected.add(i);
        }

        List<Integer> res = Collections.takeUnless(x -> x >= 7, list);

        assertEquals("wrong array size", expected.size(), res.size());
        assertArrayEquals("wrong array values", expected.toArray(), res.toArray());
    }

    /**
     * Tests right folding on TreeSet with integers.
     */
    @Test
    public void testFoldr() {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }

        assertEquals("wrong value", -5, Collections.foldr((x, z) -> x - z, 0, set).intValue());
    }

    /**
     * Tests left folding on TreeSet with integers.
     */
    @Test
    public void testFoldl() {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(i);
        }

        assertEquals("wrong value", -45, Collections.foldl((z, x) -> z - x, 0, set).intValue());
    }
}
