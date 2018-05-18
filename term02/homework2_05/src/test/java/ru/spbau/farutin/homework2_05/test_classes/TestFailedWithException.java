package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestFailedWithException {
    @Test
    public void test() throws Exception {
        throw new Exception();
    }
}
