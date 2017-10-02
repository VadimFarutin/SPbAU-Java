package ru.spbau.farutin.homework03;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

/**
 * VertexTest.java - test class for Trie.Vertex.
 */
public class VertexTest {
    /**
     * Tests getter for size on empty vertex.
     */
    @Test
    public void testGetSizeEmpty() {
        Trie.Vertex vertex = new Trie.Vertex();

        assertEquals("wrong size",0, vertex.getSize());
    }

    /**
     * Tests getter for size on vertex with some strings stored.
     */
    @Test
    public void testGetSizeNotEmpty() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("a");

        assertEquals("wrong size",1, vertex.getSize());
    }

    /**
     * Tests add() on vertex, which already had new string.
     */
    @Test
    public void testAddContained() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("a");

        assertEquals("missed existed string",true, vertex.add("a"));
        assertEquals("wrong size",1, vertex.getSize());
    }

    /**
     * Tests add() on vertex, which did not have new string.
     */
    @Test
    public void testAddDidNotContain() {
        Trie.Vertex vertex = new Trie.Vertex();

        assertEquals("nonexistent string found",false, vertex.add("a"));
        assertEquals("wrong size",1, vertex.getSize());
    }

    /**
     * Tests contain() on vertex, which contained given string.
     */
    @Test
    public void testContainsContained() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("a");

        assertEquals("missed existed string",true, vertex.contains("a"));
    }

    /**
     * Tests contain() on vertex, which did not contain given string.
     */
    @Test
    public void testContainsDidNotContain() {
        Trie.Vertex vertex = new Trie.Vertex();

        assertEquals("nonexistent string found",false, vertex.contains("a"));
    }

    /**
     * Tests howManyStartsWithPrefix() on vertex without given prefix.
     */
    @Test
    public void testHowManyStartsWithPrefixNotExisted() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("first");
        vertex.add("second");

        assertEquals("wrong quantity",0, vertex.howManyStartsWithPrefix("foo"));
    }

    /**
     * Tests howManyStartsWithPrefix() on vertex, which stores some strings with given prefix.
     */
    @Test
    public void testHowManyStartsWithPrefixExisted() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("hello first");
        vertex.add("second");
        vertex.add("hello second");
        vertex.add("hell second");
        vertex.add("hello");

        assertEquals("wrong quantity",3, vertex.howManyStartsWithPrefix("hello"));
    }

    /**
     * Tests remove() on vertex, which had given string.
     */
    @Test
    public void testRemoveContained() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("a");

        assertEquals("missed existed string",true, vertex.remove("a"));
        assertEquals("wrong size",0, vertex.getSize());
    }

    /**
     * Tests remove() on vertex without given string.
     */
    @Test
    public void testRemoveDidNotContain() {
        Trie.Vertex vertex = new Trie.Vertex();

        assertEquals("nonexistent string found",false, vertex.remove("a"));
        assertEquals("wrong size",0, vertex.getSize());
    }

    /**
     * Tests serialize(), printing vertex to ByteArrayOutputStream.
     */
    @Test
    public void testSerialize() {
        Trie.Vertex vertex = new Trie.Vertex();
        vertex.add("a");
        vertex.add("ab");
        vertex.add("ac");

        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put((byte)0);
        byteBuffer.putInt((int)'a');
        byteBuffer.put((byte)1);
        byteBuffer.putInt((int)'b');
        byteBuffer.put((byte)1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt((int)'c');
        byteBuffer.put((byte)1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            vertex.serialize(byteArrayOutputStream);

            assertArrayEquals("wrong output", byteBuffer.array(),
                              byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            assertTrue("IOException happened", false);
        }
    }

    /**
     * Tests deserialize(), reading bytes from ByteArrayInputStream.
     */
    @Test
    public void testDeserialize() {
        Trie.Vertex vertex = new Trie.Vertex();

        ByteBuffer byteBuffer = ByteBuffer.allocate(32);
        byteBuffer.put((byte)0);
        byteBuffer.putInt((int)'a');
        byteBuffer.put((byte)1);
        byteBuffer.putInt((int)'b');
        byteBuffer.put((byte)1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt((int)'c');
        byteBuffer.put((byte)1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);
        byteBuffer.putInt(Trie.ALPHABET_SIZE + 1);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array());
        try {
            vertex.deserialize(byteArrayInputStream);

            assertEquals("wrong size", 3, vertex.getSize());
            assertEquals("missed existed string",true, vertex.contains("a"));
            assertEquals("missed existed string",true, vertex.contains("ab"));
            assertEquals("missed existed string",true, vertex.contains("ac"));
        } catch (IOException e) {
            assertTrue("IOException happened", false);
        }
    }
}