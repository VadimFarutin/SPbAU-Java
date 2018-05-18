package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.After;
import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestAfter {
    private static boolean called = false;

    @After
    public void tearDown() {
        called = true;
    }

    @Test
    public void test() {
    }

    public static boolean isCalled() {
        return called;
    }
}
