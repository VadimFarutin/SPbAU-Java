package ru.spbau.farutin.homework08;

import java.util.NoSuchElementException;

/**
 * Stack - implementation of simple stack with generic values.
 * @param <T> type of stored elements
 */
public class Stack<T> {
    private Node head = null;

    /**
     * Tells whether stack is empty or not.
     * @return true if stack is empty, false otherwise
     */
    public boolean empty() {
        return head == null;
    }

    /**
     * Adds new element at the end.
     * @param e element to add
     */
    public void push(T e) {
        head = new Node(e, head);
    }

    /**
     * Access to the top element.
     * @return element at the end of the stack
     * @throws NoSuchElementException if stack is empty
     */
    public T top() throws NoSuchElementException {
        if (empty()) {
            throw new NoSuchElementException("empty stack");
        }

        return head.value;
    }

    /**
     * Gives top element and deletes it from the stack.
     * @return element at the end of the stack
     * @throws NoSuchElementException if stack is empty
     */
    public T pop() throws NoSuchElementException {
        if (empty()) {
            throw new NoSuchElementException("empty stack");
        }

        T result = head.value;
        head = head.prev;
        return result;
    }

    /**
     * Node - subclass to store values in stack.
     */
    private class Node {
        private T value;
        private Node prev = null;

        Node(T e, Node prev) {
            value = e;
            this.prev = prev;
        }
    }
}
