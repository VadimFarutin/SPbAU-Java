package ru.spbau.farutin.homework05_2;

import org.jetbrains.annotations.NotNull;

/**
 * Set.class - implementation of binary search tree.
 * @param <T> generic type of stored values
 */
public class Set<T> {
    private int size = 0;
    private Node<T> head = null;

    /**
     * Getter for size.
     * @return number of stored values
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether set contains given value or not.
     * @param value value to find
     * @return true if set contains value, false otherwise
     */
    public boolean contains(T value) {
        Node<T> current = head;

        while (current != null) {
            if (current.value.equals(value)) {
                return true;
            }

            if (current.value.hashCode() > value.hashCode()) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return false;
    }

    /**
     * Adds new value to set.
     * @param value value to add
     * @return true if value did not exist in set, false otherwise
     */
    public boolean add(T value) {
        if (head == null) {
            head = new Node<T>(value);
            size++;
            return true;
        }

        boolean isAdded = addToNode(head, value);
        if (isAdded) {
            size++;
        }

        return isAdded;
    }

    /**
     * Adds new value to node recursively.
     * @param node root node in which value should be added
     * @param value value to add
     * @return true if value did not exist in tree, false otherwise
     */
    private boolean addToNode(@NotNull Node<T> node, T value) {
        if (node.value.equals(value)) {
            return false;
        }

        if (node.value.hashCode() > value.hashCode()) {
            if (node.left == null) {
                node.left = new Node<T>(value);
                return true;
            }

            return addToNode(node.left, value);
        }

        if (node.right == null) {
            node.right = new Node<T>(value);
            return true;
        }

        return addToNode(node.right, value);
    }

    /**
     * Node - subclass representing node in set.
     * @param <U> generic type of stored value
     */
    private class Node<U> {
        U value;
        Node<U> left = null;
        Node<U> right = null;

        private Node(U value) {
            this.value = value;
        }
    }
}
