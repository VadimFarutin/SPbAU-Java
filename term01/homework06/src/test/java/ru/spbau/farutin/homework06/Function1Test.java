package ru.spbau.farutin.homework06;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Function1Test - tests for Function1.
 */
public class Function1Test {
    /**
     * Tests composition of functions with same types of arguments.
     */
    @Test
    public void testComposeOneType() {
        Function1<Integer, Integer> f = x -> x + 5;
        Function1<Integer, Integer> g = x -> x * x;
        Function1<Integer, Integer> gf = f.compose(g);

        assertEquals("wrong composition value", 49, gf.apply(2).intValue());
    }

    /**
     * Tests composition of functions with different types of arguments.
     */
    @Test
    public void testComposeDifferentTypes() {
        Function1<String, Integer> f = s -> s.length();
        Function1<Integer, Boolean> g = x -> x % 2 == 0;
        Function1<String, Boolean> gf = f.compose(g);

        assertEquals("wrong composition value", false, gf.apply("Hello"));
    }
}
