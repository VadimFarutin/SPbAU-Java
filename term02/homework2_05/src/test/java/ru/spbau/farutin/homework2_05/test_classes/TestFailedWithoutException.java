package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestFailedWithoutException {
    @Test(expected = Exception.class)
    public void test() throws Exception {
    }
}
