package ru.spbau.farutin.homework07;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyTreeSetImpl - TreeSet implementation.
 * @param <E> type of stored values
 */
public class MyTreeSetImpl<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Comparator<? super E> cmp = null;
    private int size = 0;
    private Node head = null;

    /**
     * Constructs a new, empty tree set, sorted according to the natural ordering of its elements.
     * All elements inserted into the set must implement the Comparable interface.
     */
    public MyTreeSetImpl() {
        this((o1, o2) -> ((Comparable<? super E>)o1).compareTo(o2));
    }

    /**
     * Constructs an empty tree set, sorted according to the specified comparator.
     * @param comparator the comparator that will be used to order this set
     */
    public MyTreeSetImpl(@NotNull Comparator<? super E> comparator) {
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
     * Returns an iterator over the elements in this set in descending order
     * @return an iterator over the elements in descending order
     */
    @Override
    public @NotNull Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private Node current = head == null ? null : head.lastNode();

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
                current = current.prev();

                return currentValue;
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException("Removing is not implemented");
            }
        };
    }

    /**
     * Returns a reverse order view of the elements contained in this set.
     * @return a reverse order view of this set
     */
    @Override
    public @NotNull MyTreeSet<E> descendingSet() {
        return new DescendingTreeSet(this);
    }

    /**
     * Returns the first (lowest) element currently in this set.
     * @return the first (lowest) element currently in this set
     * @throws NoSuchElementException if set is empty
     */
    @Override
    public E first() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Empty set");
        }

        return head.first();
    }

    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set
     * @throws NoSuchElementException if set is empty
     */
    @Override
    public E last() throws NoSuchElementException {
        if (head == null) {
            throw new NoSuchElementException("Empty set");
        }

        return head.last();
    }

    /**
     * Returns the greatest element in this set strictly less than the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the greatest element less than e, or null if there is no such element
     */
    @Override
    public E lower(E e) {
        return head == null ? null : head.lower(e);
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the greatest element less than or equal to e, or null if there is no such element
     */
    @Override
    public E floor(E e) {
        return head == null ? null : head.floor(e);
    }

    /**
     * Returns the least element in this set greater than or equal to the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than or equal to e, or null if there is no such element
     */
    @Override
    public E ceiling(E e) {
        return head == null ? null : head.ceiling(e);
    }

    /**
     * Returns the least element in this set strictly greater than the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than e, or null if there is no such element
     */
    @Override
    public E higher(E e) {
        return head == null ? null : head.higher(e);
    }

    /**
     * DescendingTreeSet - helper class providing reverse order view of the elements.
     */
    private class DescendingTreeSet extends MyTreeSetImpl<E> {
        private MyTreeSetImpl<E> parentTreeSet = null;

        private DescendingTreeSet(@NotNull MyTreeSetImpl<E> parentTreeSet) {
            this.parentTreeSet = parentTreeSet;
        }

        @Override
        public boolean add(E e) {
            return parentTreeSet.add(e);
        }

        @Override
        public int size() {
            return parentTreeSet.size();
        }

        @Override
        public @NotNull Iterator<E> iterator() {
            return parentTreeSet.descendingIterator();
        }

        @Override
        public @NotNull Iterator<E> descendingIterator() {
            return parentTreeSet.iterator();
        }

        @Override
        public @NotNull MyTreeSet<E> descendingSet() {
            return parentTreeSet;
        }

        @Override
        public E first() throws NoSuchElementException {
            return parentTreeSet.last();
        }

        @Override
        public E last() throws NoSuchElementException {
            return parentTreeSet.first();
        }

        @Override
        public E lower(E e) {
            return parentTreeSet.higher(e);
        }

        @Override
        public E floor(E e) {
            return parentTreeSet.ceiling(e);
        }

        @Override
        public E ceiling(E e) {
            return parentTreeSet.floor(e);
        }

        @Override
        public E higher(E e) {
            return parentTreeSet.lower(e);
        }
    }

    /**
     * Node - subclass representing node in set.
     */
    private class Node {
        private E value;
        private Node left = null;
        private Node right = null;
        private Node parent = null;

        private Node(E value, Node parent) {
            this.value = value;
            this.parent = parent;
        }

        private E first() {
            return left == null ? value : left.first();
        }

        private E last() {
            return right == null ? value : right.last();
        }

        private E lower(E e) {
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

        private E higher(E e) {
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

        private E ceiling(E e) {
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

        private E floor(E e) {
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

        private Node next() {
            if (right != null) {
                return right.firstNode();
            }

            Node current = this;
            while (current.parent != null && cmp.compare(current.parent.value, current.value) < 0) {
                current = current.parent;
            }

            return current.parent;
        }

        private @NotNull Node firstNode() {
            Node current = this;

            while (current.left != null) {
                current = current.left;
            }

            return current;
        }

        private Node prev() {
            if (left != null) {
                return left.lastNode();
            }

            Node current = this;
            while (current.parent != null && cmp.compare(current.parent.value, current.value) > 0) {
                current = current.parent;
            }

            return current.parent;
        }

        private @NotNull Node lastNode() {
            Node current = this;

            while (current.right != null) {
                current = current.right;
            }

            return current;
        }
    }
}
