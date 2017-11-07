package ru.spbau.farutin.homework06;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * PredicateTest - tests for Predicate.
 */
public class PredicateTest {
    /**
     * Tests logical and of two predicates.
     */
    @Test
    public void testAnd() {
        Predicate<Integer> p1 = x -> x > 5;
        Predicate<Integer> p2 = x -> x < 10;
        Predicate<Integer> p3 = p1.and(p2);

        assertTrue("wrong value, both predicates are true", p3.apply(7));
    }

    /**
     * Tests logical or of two predicates.
     */
    @Test
    public void testOr() {
        Predicate<String> p1 = s -> s.matches("(.*)\\.jpg");
        Predicate<String> p2 = s -> s.matches("(.*)\\.png");
        Predicate<String> p3 = p1.or(p2);

        assertFalse("wrong value, both predicates are false", p3.apply("hello.ppg"));
    }

    /**
     * Tests logical no of predicate.
     */
    @Test
    public void testNot() {
        Predicate<Function1<Integer, Integer>> p1 = f -> f.apply(0) == 0;
        Predicate<Function1<Integer, Integer>> p2 = p1.not();

        assertTrue("wrong value, original predicate is false", p2.apply(x -> x * x * x - x + 1));
    }

    /**
     * Checks ALWAYS_TRUE predicate value.
     */
    @Test
    public void testAlwaysTrue() {
        assertTrue("not always true!", Predicate.ALWAYS_TRUE.apply("whatever"));
    }

    /**
     * Checks ALWAYS_FALSE predicate value.
     */
    @Test
    public void testAlwaysFalse() {
        assertFalse("not always false!", Predicate.ALWAYS_FALSE.apply("blah blah"));
    }
}
