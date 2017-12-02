package ru.spbau.farutin.homework07;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * MyTreeSetImplTest - tests for MyTreeSetImpl.
 */
public class MyTreeSetImplTest {
    /**
     * Takes elements in reverse order.
     */
    @Test
    public void testDescendingIterator() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        Iterator<Integer> it = set.descendingIterator();

        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 5, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 4, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 3, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 2, it.next().intValue());
        assertTrue("there should be more values", it.hasNext());
        assertEquals("wrong next value", 1, it.next().intValue());

        assertFalse("there should not be any more values", it.hasNext());
    }

    /**
     * Tests descendingSet methods and it correctness after adding new values.
     */
    @Test
    public void testDescendingSet() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(6);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(1);

        MyTreeSet<Integer> desc = set.descendingSet();

        assertEquals("wrong descending set size", set.size(), desc.size());
        assertEquals("wrong descending set first", set.last(), desc.first());
        assertEquals("wrong descending set last", set.first(), desc.last());
        assertEquals("wrong descending set lower", set.higher(3), desc.lower(3));
        assertEquals("wrong descending set higher", set.lower(3), desc.higher(3));
        assertEquals("wrong descending set floor", set.ceiling(4), desc.floor(4));
        assertEquals("wrong descending set ceiling", set.floor(4), desc.ceiling(4));

        desc.add(4);
        desc.add(7);
        desc.add(2);

        assertEquals("wrong descending set size", set.size(), desc.size());
        assertEquals("wrong descending set first", set.last(), desc.first());
        assertEquals("wrong descending set last", set.first(), desc.last());
        assertEquals("wrong descending set lower", set.higher(3), desc.lower(3));
        assertEquals("wrong descending set higher", set.lower(3), desc.higher(3));
        assertEquals("wrong descending set floor", set.ceiling(4), desc.floor(4));
        assertEquals("wrong descending set ceiling", set.floor(4), desc.ceiling(4));

        set.add(-1);
        set.add(8);
        set.add(5);

        assertEquals("wrong descending set size", set.size(), desc.size());
        assertEquals("wrong descending set first", set.last(), desc.first());
        assertEquals("wrong descending set last", set.first(), desc.last());
        assertEquals("wrong descending set lower", set.higher(7), desc.lower(7));
        assertEquals("wrong descending set higher", set.lower(0), desc.higher(0));
        assertEquals("wrong descending set floor", set.ceiling(4), desc.floor(4));
        assertEquals("wrong descending set ceiling", set.floor(4), desc.ceiling(4));
    }

    /**
     * Tests first() on an empty set.
     */
    @Test(expected = NoSuchElementException.class)
    public void testFirstEmpty() {
        new MyTreeSetImpl<Integer>().first();
    }

    /**
     * Tests first() on a not empty set.
     */
    @Test
    public void testFirstNotEmpty() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertEquals("wrong first element", 1, set.first().intValue());
    }

    /**
     * Tests last() on an empty set.
     */
    @Test(expected = NoSuchElementException.class)
    public void testLastEmpty() {
        new MyTreeSetImpl<Integer>().last();
    }

    /**
     * Tests last() on a not empty set.
     */
    @Test
    public void testLastNotEmpty() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertEquals("wrong first element", 5, set.last().intValue());
    }

    /**
     * Tests lower() for minimum element.
     */
    @Test
    public void testLowerNotExists() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertTrue("there is no lower for first element", set.lower(1) == null);
    }

    /**
     * Tests lower() for not minimum element.
     */
    @Test
    public void testLowerExists() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertTrue("lower exists and it is not null", set.lower(3) != null);
        assertEquals("wrong element", 2, set.lower(3).intValue());
    }

    /**
     * Tests floor() when there no value that equals matching value.
     */
    @Test
    public void testFloorNotEqual() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(6);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(1);

        assertTrue("floor exists and it is not null", set.floor(4) != null);
        assertEquals("wrong element", 3, set.floor(4).intValue());
    }

    /**
     * Tests floor() when set contains matching value.
     */
    @Test
    public void testFloorEqual() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(6);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(1);

        assertTrue("floor exists and it is not null", set.floor(3) != null);
        assertEquals("wrong element", 3, set.floor(3).intValue());
    }

    /**
     * Tests ceiling() when there no value that equals matching value.
     */
    @Test
    public void testCeilingNotEqual() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(6);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(1);

        assertTrue("ceiling exists and it is not null", set.ceiling(4) != null);
        assertEquals("wrong element", 5, set.ceiling(4).intValue());
    }

    /**
     * Tests ceiling() when set contains matching value.
     */
    @Test
    public void testCeilingEqual() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(6);
        set.add(3);
        set.add(2);
        set.add(5);
        set.add(1);

        assertTrue("ceiling exists and it is not null", set.ceiling(3) != null);
        assertEquals("wrong element", 3, set.ceiling(3).intValue());
    }

    /**
     * Tests higher() for maximum element.
     */
    @Test
    public void testHigherNotExists() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertTrue("there is no higher for last element", set.higher(5) == null);
    }

    /**
     * Tests higher() for not maximum element.
     */
    @Test
    public void testHigherExists() {
        MyTreeSetImpl<Integer> set = new MyTreeSetImpl<>();
        set.add(5);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(1);

        assertTrue("higher exists and it is not null", set.higher(3) != null);
        assertEquals("wrong element", 4, set.higher(3).intValue());
    }

    /**
     * Tests constructor with comparator.
     */
    @Test
    public void testConstructor() {
        MyTreeSetImpl<String> set = new MyTreeSetImpl<>((o1, o2) -> {
            if (o1.length() < o2.length()) {
                return -1;
            }

            if (o1.length() == o2.length()) {
                return 0;
            }

            return 1;
        });

        set.add("hello");
        set.add("a");
        set.add("hell");
        set.add("hell!");

        assertEquals("wrong set size", 3, set.size());
        assertEquals("wrong set last", "hello", set.last());
        assertEquals("wrong set first", "a", set.first());
        assertEquals("wrong set lower", "a", set.lower("four"));
        assertEquals("wrong set higher", "hell", set.higher("two"));
        assertEquals("wrong set floor", "a", set.floor("two"));
        assertEquals("wrong set ceiling", "hello", set.ceiling("hell!"));
    }
}
