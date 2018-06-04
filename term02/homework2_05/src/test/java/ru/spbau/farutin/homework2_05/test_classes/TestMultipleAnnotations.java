package ru.spbau.farutin.homework2_05.test_classes;

import ru.spbau.farutin.homework2_05.annotations.After;
import ru.spbau.farutin.homework2_05.annotations.Before;
import ru.spbau.farutin.homework2_05.annotations.Test;

public class TestMultipleAnnotations {
    @After
    public void tearDown() {
    }

    @Test
    public void test() {
    }

    @Before
    @Test
    public void wrongMethod() {
    }
}
