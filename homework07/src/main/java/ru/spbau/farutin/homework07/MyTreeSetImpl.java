package ru.spbau.farutin.homework07;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MyTreeSetImpl - TreeSet implementation.
 * @param <E> type of stored values
 */
public class MyTreeSetImpl<E> extends Set<E> implements MyTreeSet<E> {
    /**
     * Constructs a new, empty tree set, sorted according to the natural ordering of its elements.
     * All elements inserted into the set must implement the Comparable interface.
     */
    public MyTreeSetImpl() {
        super();
    }

    /**
     * Constructs an empty tree set, sorted according to the specified comparator.
     * @param comparator the comparator that will be used to order this set
     */
    public MyTreeSetImpl(@NotNull Comparator<? super E> comparator) {
        super(comparator);
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

                E currentValue = current.getValue();
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
}
