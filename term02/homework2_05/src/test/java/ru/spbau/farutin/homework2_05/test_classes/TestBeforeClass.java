package ru.spbau.farutin.homework2_05.test_classes;

public class TestBeforeClass {
    private static boolean called = false;

    @ru.spbau.farutin.homework2_05.annotations.BeforeClass
    public static void init() {
        called = true;
    }

    public static boolean isCalled() {
        return called;
    }
}
