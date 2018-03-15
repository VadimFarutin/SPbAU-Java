package ru.spbau.farutin.homework03;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.junit.Assert.*;

/**
 * TrieTest.java - test class for Trie.
 */
public class TrieTest {
    /**
     * Tests getter for size on empty trie.
     */
    @Test
    public void testSizeEmpty() {
        Trie trie = new Trie();

        assertEquals("wrong size",0, trie.size());
    }

    /**
     * Tests getter for size on trie with some strings stored.
     */
    @Test
    public void testSizeNotEmpty() {
        Trie trie = new Trie();
        trie.add("a");

        assertEquals("wrong size",1, trie.size());
    }

    /**
     * Tests add() on trie, which already had new string.
     */
    @Test
    public void testAddContained() {
        Trie trie = new Trie();
        trie.add("a");

        assertEquals("missed existed string",false, trie.add("a"));
        assertEquals("wrong size",1, trie.size());
    }

    /**
     * Tests add() on trie, which did not have new string.
     */
    @Test
    public void testAddDidNotContain() {
        Trie trie = new Trie();

        assertEquals("nonexistent string found",true, trie.add("a"));
        assertEquals("wrong size",1, trie.size());
    }

    /**
     * Tests contain() on trie, which contained given string.
     */
    @Test
    public void testContainsContained() {
        Trie trie = new Trie();
        trie.add("a");

        assertEquals("missed existed string",true, trie.contains("a"));
    }

    /**
     * Tests contain() on trie, which did not contain given string.
     */
    @Test
    public void testContainsDidNotContain() {
        Trie trie = new Trie();

        assertEquals("nonexistent string found",false, trie.contains("a"));
    }

    /**
     * Tests howManyStartsWithPrefix() on trie without given prefix.
     */
    @Test
    public void testHowManyStartsWithPrefixNotExisted() {
        Trie trie = new Trie();
        trie.add("first");
        trie.add("second");

        assertEquals("wrong quantity",0, trie.howManyStartsWithPrefix("foo"));
    }

    /**
     * Tests howManyStartsWithPrefix() on trie, which stores some strings with given prefix.
     */
    @Test
    public void testHowManyStartsWithPrefixExisted() {
        Trie trie = new Trie();
        trie.add("hello first");
        trie.add("second");
        trie.add("hello second");
        trie.add("hell second");
        trie.add("hello");

        assertEquals("wrong quantity",3, trie.howManyStartsWithPrefix("hello"));
    }

    /**
     * Tests remove() on trie, which had given string.
     */
    @Test
    public void testRemoveContained() {
        Trie trie = new Trie();
        trie.add("a");

        assertEquals("missed existed string",true, trie.remove("a"));
        assertEquals("wrong size",0, trie.size());
    }

    /**
     * Tests remove() on trie without given string.
     */
    @Test
    public void testRemoveDidNotContain() {
        Trie trie = new Trie();

        assertEquals("nonexistent string found",false, trie.remove("a"));
        assertEquals("wrong size",0, trie.size());
    }

    /**
     * Tests serialize(), printing trie to ByteArrayOutputStream.
     */
    @Test
    public void testSerialize() {
        Trie trie = new Trie();
        trie.add("a");
        trie.add("ab");
        trie.add("ac");

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
            trie.serialize(byteArrayOutputStream);

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
        Trie trie = new Trie();

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
            trie.deserialize(byteArrayInputStream);

            assertEquals("wrong size", 3, trie.size());
            assertEquals("missed existed string",true, trie.contains("a"));
            assertEquals("missed existed string",true, trie.contains("ab"));
            assertEquals("missed existed string",true, trie.contains("ac"));
        } catch (IOException e) {
            assertTrue("IOException happened", false);
        }
    }
}