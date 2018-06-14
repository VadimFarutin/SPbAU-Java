package ru.spbau.farutin.homework2_05.test_classes;

public class TestBeforeClassMany {
    private static boolean called = false;

    @ru.spbau.farutin.homework2_05.annotations.BeforeClass
    public static void init() {
        called = true;
    }

    @ru.spbau.farutin.homework2_05.annotations.BeforeClass
    public static void init2() {
        called = true;
    }

    public static boolean isCalled() {
        return called;
    }
}
