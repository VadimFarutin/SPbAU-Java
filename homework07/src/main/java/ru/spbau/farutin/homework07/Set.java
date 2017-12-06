package ru.spbau.farutin.homework07;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Set - simple set implementation.
 * @param <E> type of stored values
 */
public class Set<E> extends AbstractSet<E> {
    private Comparator<? super E> cmp = null;
    private int size = 0;
    protected Node head = null;

    /**
     * Constructs a new, empty set, sorted according to the natural ordering of its elements.
     * All elements inserted into the set must implement the Comparable interface.
     */
    @SuppressWarnings("unchecked")
    public Set() {
        this((o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    /**
     * Constructs an empty set, sorted according to the specified comparator.
     * @param comparator the comparator that will be used to order this set
     */
    public Set(@NotNull Comparator<? super E> comparator) {
        cmp = comparator;
    }

    /**
     * Returns an iterator over the elements in this set in ascending order.
     * @return iterator over the elements
     */
    @Override
    public @NotNull Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node current = head == null ? null : head.firstNode();

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }

                E currentValue = current.value;
                current = current.next();

                return currentValue;
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Removing is not implemented");
            }
        };
    }

    /**
     * Getter for size.
     * @return number of stored values
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Adds new value to set.
     * @param e value to add
     * @return true if value did not exist in set, false otherwise
     */
    @Override
    public boolean add(E e) {
        if (head == null) {
            head = new Node(e, null);
            size++;
            return true;
        }

        boolean isAdded = addToNode(head, e);
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
    private boolean addToNode(@NotNull Node node, E value) {
        int cmpResult = cmp.compare(node.value, value);

        if (cmpResult == 0) {
            return false;
        }

        if (cmpResult > 0) {
            if (node.left == null) {
                node.left = new Node(value, node);
                return true;
            }

            return addToNode(node.left, value);
        }

        if (node.right == null) {
            node.right = new Node(value, node);
            return true;
        }

        return addToNode(node.right, value);
    }

    /**
     * Node - subclass representing node in set.
     */
    protected class Node {
        private E value;
        private Node left = null;
        private Node right = null;
        private Node parent = null;

        private Node(E value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        protected E getValue() {
            return value;
        }

        protected E first() {
            return left == null ? value : left.first();
        }

        protected E last() {
            return right == null ? value : right.last();
        }

        protected E lower(E e) {
            int cmpResult = cmp.compare(value, e);

            if (cmpResult == 0) {
                return left == null ? null : left.last();
            }

            if (cmpResult < 0) {
                if (right == null) {
                    return value;
                }

                E rightLower = right.lower(e);

                return rightLower == null ? value : rightLower;
            }

            return left == null ? null : left.lower(e);
        }

        protected E higher(E e) {
            int cmpResult = cmp.compare(value, e);

            if (cmpResult == 0) {
                return right == null ? null : right.first();
            }

            if (cmpResult > 0) {
                if (left == null) {
                    return value;
                }

                E leftHigher = left.higher(e);

                return leftHigher == null ? value : leftHigher;
            }

            return right == null ? null : right.higher(e);
        }

        protected E ceiling(E e) {
            int cmpResult = cmp.compare(value, e);

            if (cmpResult == 0) {
                return value;
            }

            if (cmpResult > 0) {
                if (left == null) {
                    return value;
                }

                E leftCeiling = left.ceiling(e);

                return leftCeiling == null ? value : leftCeiling;
            }

            return right == null ? null : right.ceiling(e);
        }

        protected E floor(E e) {
            int cmpResult = cmp.compare(value, e);

            if (cmpResult == 0) {
                return value;
            }

            if (cmpResult < 0) {
                if (right == null) {
                    return value;
                }

                E rightFloor = right.floor(e);

                return rightFloor == null ? value : rightFloor;
            }

            return left == null ? null : left.floor(e);
        }

        protected Node next() {
            if (right != null) {
                return right.firstNode();
            }

            Node current = this;
            while (current.parent != null && cmp.compare(current.parent.value, current.value) < 0) {
                current = current.parent;
            }

            return current.parent;
        }

        protected @NotNull Node firstNode() {
            Node current = this;

            while (current.left != null) {
                current = current.left;
            }

            return current;
        }

        protected Node prev() {
            if (left != null) {
                return left.lastNode();
            }

            Node current = this;
            while (current.parent != null && cmp.compare(current.parent.value, current.value) > 0) {
                current = current.parent;
            }

            return current.parent;
        }

        protected @NotNull Node lastNode() {
            Node current = this;

            while (current.right != null) {
                current = current.right;
            }

            return current;
        }
    }
}
