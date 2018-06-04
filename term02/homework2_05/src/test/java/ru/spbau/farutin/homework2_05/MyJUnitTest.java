package ru.spbau.farutin.homework2_05;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.spbau.farutin.homework2_05.test_classes.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for MyJUnit.
 */
public class MyJUnitTest {
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeClass
    public static void init() {
        PrintStream printStream = new PrintStream(output);
        System.setOut(printStream);
    }

    @Test
    public void testBeforeClass() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestBeforeClass.class);
        myJUnit.test();

        assertTrue(TestBeforeClass.isCalled());
    }

    @Test
    public void testAfterClass() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestAfterClass.class);
        myJUnit.test();

        assertTrue(TestAfterClass.isCalled());
    }

    @Test(expected = IllegalTestSuiteConfigurationException.class)
    public void testBeforeClassMany() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestBeforeClassMany.class);
        myJUnit.test();
    }

    @Test(expected = IllegalTestSuiteConfigurationException.class)
    public void testAfterClassMany() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestAfterClassMany.class);
        myJUnit.test();
    }

    @Test(expected = IllegalTestSuiteConfigurationException.class)
    public void testMultipleAnnotations() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestMultipleAnnotations.class);
        myJUnit.test();
    }

    @Test
    public void testBefore() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestBefore.class);
        myJUnit.test();

        assertTrue(TestBefore.isCalled());
        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestBefore.test " +
                "finished in "));
    }

    @Test
    public void testAfter() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestAfter.class);
        myJUnit.test();

        assertTrue(TestAfter.isCalled());
        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestAfter.test " +
                "finished in "));
    }

    @Test
    public void testNotIgnore() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestNotIgnored.class);
        myJUnit.test();

        assertTrue(TestNotIgnored.isCalled());
        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestNotIgnored.test " +
                "finished in "));
    }

    @Test
    public void testIgnore() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestIgnored.class);
        myJUnit.test();

        assertFalse(TestIgnored.isCalled());
        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestIgnored.test " +
                "ignored: skip"));
    }

    @Test
    public void testFailedWithException() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestFailedWithException.class);
        myJUnit.test();

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestFailedWithException.test " +
                "failed: "));
    }

    @Test
    public void testFailedWithoutException() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestFailedWithoutException.class);
        myJUnit.test();

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestFailedWithoutException.test " +
                "failed: expected " +
                Exception.class.getName()));
    }

    @Test
    public void testFailedWrongException() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestFailedWrongException.class);
        myJUnit.test();

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestFailedWrongException.test " +
                "failed: "));
    }

    @Test
    public void testCorrectException() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestCorrectException.class);
        myJUnit.test();

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestCorrectException.test " +
                "finished in "));
    }

    @Test
    public void testManyMethods() throws Exception {
        MyJUnit myJUnit = new MyJUnit(TestManyMethods.class);
        myJUnit.test();

        TestManyMethods.verify();

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.test1 " +
                "finished in "));

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.test2 " +
                "ignored: skip"));

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.test3 " +
                "ignored: bugs"));

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.test4 " +
                "failed: "));

        assertTrue(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.test5 " +
                "failed: "));

        assertFalse(output.toString().contains(
                "ru.spbau.farutin.homework2_05.test_classes.TestManyMethods.notAnnotated"));
    }
}
