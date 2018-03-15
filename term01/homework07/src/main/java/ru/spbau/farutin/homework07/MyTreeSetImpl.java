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
    private Tree<E> dataTree = null;

    /**
     * Constructs a new, empty tree set, sorted according to the natural ordering of its elements.
     * All elements inserted into the set must implement the Comparable interface.
     */
    public MyTreeSetImpl() {
        dataTree = new Tree<>();
    }

    /**
     * Constructs an empty tree set, sorted according to the specified comparator.
     * @param comparator the comparator that will be used to order this set
     */
    public MyTreeSetImpl(@NotNull Comparator<? super E> comparator) {
        dataTree = new Tree<>(comparator);
    }

    /**
     * Returns an iterator over the elements in this set in ascending order.
     * @return iterator over the elements
     */
    @Override
    public @NotNull Iterator<E> iterator() {
        return dataTree.iterator();
    }

    /**
     * Getter for size.
     * @return number of stored values
     */
    @Override
    public int size() {
        return dataTree.size();
    }

    /**
     * Adds new value to set.
     * @param e value to add
     * @return true if value did not exist in set, false otherwise
     */
    @Override
    public boolean add(E e) {
        return dataTree.add(e);
    }

    /**
     * Returns an iterator over the elements in this set in descending order
     * @return an iterator over the elements in descending order
     */
    @Override
    public @NotNull Iterator<E> descendingIterator() {
        return dataTree.descendingIterator();
    }

    /**
     * Returns the first (lowest) element currently in this set.
     * @return the first (lowest) element currently in this set
     * @throws NoSuchElementException if set is empty
     */
    @Override
    public E first() throws NoSuchElementException {
        return dataTree.first();
    }

    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set
     * @throws NoSuchElementException if set is empty
     */
    @Override
    public E last() throws NoSuchElementException {
        return dataTree.last();
    }

    /**
     * Returns the greatest element in this set strictly less than the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the greatest element less than e, or null if there is no such element
     */
    @Override
    public E lower(E e) {
        return dataTree.lower(e);
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the greatest element less than or equal to e, or null if there is no such element
     */
    @Override
    public E floor(E e) {
        return dataTree.floor(e);
    }

    /**
     * Returns the least element in this set greater than or equal to the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than or equal to e, or null if there is no such element
     */
    @Override
    public E ceiling(E e) {
        return dataTree.ceiling(e);
    }

    /**
     * Returns the least element in this set strictly greater than the given element,
     * or null if there is no such element.
     * @param e the value to match
     * @return the least element greater than e, or null if there is no such element
     */
    @Override
    public E higher(E e) {
        return dataTree.higher(e);
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
