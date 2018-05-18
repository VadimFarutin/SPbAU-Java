package ru.spbau.farutin.homework2_05.test_classes;

public class TestAfterClass {
    private static boolean called = false;

    @ru.spbau.farutin.homework2_05.annotations.AfterClass
    public static void finish() {
        called = true;
    }

    public static boolean isCalled() {
        return called;
    }
}
