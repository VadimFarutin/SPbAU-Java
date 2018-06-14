package ru.spbau.farutin.homework2_05.test_classes;

public class TestAfterClassMany {
    private static boolean called = false;

    @ru.spbau.farutin.homework2_05.annotations.AfterClass
    public static void finish() {
        called = true;
    }

    @ru.spbau.farutin.homework2_05.annotations.AfterClass
    public static void finish2() {
        called = true;
    }

    public static boolean isCalled() {
        return called;
    }
}
