package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.Test;

import java.io.IOException;

public class TestCorrectException {
    @Test(expected = IOException.class)
    public void test() throws Exception {
        throw new IOException();
    }
}
