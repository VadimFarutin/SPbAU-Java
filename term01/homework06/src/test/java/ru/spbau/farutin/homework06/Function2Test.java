package ru.spbau.farutin.homework06;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Function2Test - tests for Function2.
 */
public class Function2Test {
    /**
     * Tests composition of functions with same types of arguments.
     */
    @Test
    public void testComposeOneType() {
        Function2<Integer, Integer, Integer> f = (x, y) -> x + y;
        Function1<Integer, Integer> g = x -> x * x;
        Function2<Integer, Integer, Integer> gf = f.compose(g);

        assertEquals("wrong composition value", 49, gf.apply(2, 5).intValue());
    }

    /**
     * Tests composition of functions with different types of arguments.
     */
    @Test
    public void testComposeDifferentTypes() {
        Function2<String, String, Integer> f = (s, t) -> s.indexOf(t);
        Function1<Integer, Boolean> g = x -> x != -1;
        Function2<String, String, Boolean> gf = f.compose(g);

        assertEquals("wrong composition value", true, gf.apply("Hello, world!", "Hell"));
    }

    /**
     * Tests first argument binding.
     */
    @Test
    public void testBind1() {
        Function2<Double, Double, Double> f = (p, r) -> p * r * r;
        Function1<Double, Double> g = f.bind1(3.14);

        assertEquals("wrong bind1 result", 12.56, g.apply(2.0), 0.001);
    }

    /**
     * Tests second argument binding.
     */
    @Test
    public void testBind2() {
        Function2<String, String, String> f = (s, t) -> s.concat(t);
        Function1<String, String> g = f.bind2("!!!");

        assertEquals("wrong bind2 result", "Hello!!!", g.apply("Hello"));
    }

    /**
     * Tests currying.
     */
    @Test
    public void testCurry() {
        Function2<String, String, String> f = (s, t) -> s.concat(t);
        Function1<String, Function1<String, String>> g = f.curry();

        assertEquals("wrong curry result", "Hello!!!", g.apply("Hello").apply("!!!"));
    }
}
