package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestManyMethods {
    private static List<String> list = null;
    private static int cntUp = 0;
    private static int cntDown = 0;

    @BeforeClass
    public static void init() {
        list = new ArrayList<>();
    }

    @Before
    public void setUp1() {
        list.add("setUp1");
    }

    @Before
    public void setUp2() {
        cntUp++;
    }

    @Test
    public void test1() {
        assertTrue(2 + 2 == 4);
    }

    @Test(expected = IOException.class, ignore = "skip")
    public void test2() throws Exception {
        throw new IOException();
    }

    @Test(ignore = "bugs")
    public void test3() {
        assertTrue(2 + 2 == 5);
    }

    @Test
    public void test4() {
        assertTrue(2 + 2 == 3);
    }

    @Test(expected = NullPointerException.class)
    public void test5() throws Exception {
        throw new IOException();
    }

    public void notAnnotated() {
        assertTrue(true);
    }

    @After
    public void tearDown1() {
        list.add("tearDown1");
    }

    @After
    public void tearDown2() {
        cntDown++;
    }

    @AfterClass
    public static void finish() {
        list.add("finish");
    }

    public static void verify() {
        List<String> expected = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            expected.add("setUp1");
            expected.add("tearDown1");
        }

        expected.add("finish");

        assertEquals(list, expected);
        assertEquals(cntUp, cntDown);
        assertEquals(3, cntUp);
    }
}
