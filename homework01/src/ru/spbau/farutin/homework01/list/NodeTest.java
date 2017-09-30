package ru.spbau.farutin.homework01.list;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NodeTest.java - unit tests for List.Node class.
 */
public class NodeTest extends List.Node {
    /**
     * These tests check whether node constructed with correct fields or not.
     */
    @Test
    public void testNodeConstructorKey() {
        List.Node nextNode = new List.Node("first", "first value", null, null);
        List.Node prevNode = new List.Node("second", "second value", null, null);
        String testedKey = "tested key";
        List.Node testedNode = new List.Node(testedKey, "tested value", nextNode, prevNode);

        assertEquals(testedKey, testedNode.key, "wrong key");
    }

    @Test
    public void testNodeConstructorValue() {
        List.Node nextNode = new List.Node("first", "first value", null, null);
        List.Node prevNode = new List.Node("second", "second value", null, null);
        String testedValue = "tested value";
        List.Node testedNode = new List.Node("tested key", testedValue, nextNode, prevNode);

        assertEquals(testedValue, testedNode.value, "wrong value");
    }

    @Test
    public void testNodeConstructorNext() {
        List.Node nextNode = new List.Node("first", "first value", null, null);
        List.Node prevNode = new List.Node("second", "second value", null, null);
        List.Node testedNode = new List.Node("tested key", "tested value", nextNode, prevNode);

        assertEquals(nextNode, testedNode.next, "wrong next Node");
    }

    @Test
    public void testNodeConstructorPrev() {
        List.Node nextNode = new List.Node("first", "first value", null, null);
        List.Node prevNode = new List.Node("second", "second value", null, null);
        List.Node testedNode = new List.Node("tested key", "tested value", nextNode, prevNode);

        assertEquals(prevNode, testedNode.prev, "wrong prev Node");
    }
}