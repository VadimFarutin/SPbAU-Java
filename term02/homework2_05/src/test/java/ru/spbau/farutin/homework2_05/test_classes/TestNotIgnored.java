package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestNotIgnored {
    private static boolean called = false;

    @Test
    public void test() {
        called = true;
    }

    public static boolean isCalled() {
        return called;
    }
}
