package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.Before;
import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestBefore {
    private static boolean called = false;

    @Before
    public void setUp() {
        called = true;
    }

    @Test
    public void test() {
    }

    public static boolean isCalled() {
        return called;
    }
}
