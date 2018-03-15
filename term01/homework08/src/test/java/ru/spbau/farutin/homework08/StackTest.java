package ru.spbau.farutin.homework08;

import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

/**
 * StackTest - tests for Stack.
 */
public class StackTest {
    /**
     * Tests empty() on empty stack.
     */
    @Test
    public void testEmptyTrue() {
        Stack<Integer> stack = new Stack<>();

        assertTrue("stack should be empty", stack.empty());
    }

    /**
     * Tests empty() on not empty stack.
     */
    @Test
    public void testEmptyFalse() {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(3);
        stack.pop();

        assertFalse("stack should not be empty", stack.empty());
    }

    /**
     * Test for push() method.
     */
    @Test
    public void testPush() {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(3);

        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 3, stack.pop().intValue());
        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 5, stack.pop().intValue());
    }

    /**
     * Tests top() on empty stack.
     */
    @Test(expected = NoSuchElementException.class)
    public void testTopEmpty() {
        Stack<Integer> stack = new Stack<>();
        int top = stack.top();
    }

    /**
     * Tests top() on not empty stack.
     */
    @Test
    public void testTopNotEmpty() {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(3);

        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 3, stack.top().intValue());

        stack.pop();

        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 5, stack.top().intValue());
    }

    /**
     * Tests pop() on empty stack.
     */
    @Test(expected = NoSuchElementException.class)
    public void testPopEmpty() {
        Stack<Integer> stack = new Stack<>();
        int top = stack.pop();
    }

    /**
     * Tests pop() on not empty stack.
     */
    @Test
    public void testPopNotEmpty() {
        Stack<Integer> stack = new Stack<>();
        stack.push(5);
        stack.push(3);

        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 3, stack.pop().intValue());
        assertFalse("stack should not be empty", stack.empty());
        assertEquals("wrong value", 5, stack.pop().intValue());
        assertTrue("stack should be empty", stack.empty());
    }
}
