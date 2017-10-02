package ru.spbau.farutin.homework03;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Trie.java - this class implements basic trie functionality.
 */
public class Trie implements Serializable {
    public static final int ALPHABET_SIZE = Character.MAX_VALUE;

    private Vertex head = new Vertex();

    /**
     * Getter for trie size.
     * @return quantity of strings stored in trie
     */
    public int size() {
        return head.getSize();
    }

    /**
     * Adds new element.
     * @param element new string to add
     * @return true if trie did not contain element before adding, false otherwise
     */
    public boolean add(String element) {
        return !head.add(element);
    }

    /**
     * Checks whether trie contains element.
     * @param element string to find in trie
     * @return true if trie contains element, false otherwise
     */
    public boolean contains(String element) {
        return head.contains(element);
    }

    /**
     * Counts quantity of stored strings with given prefix.
     * @param prefix common prefix to find
     * @return quantity of stored strings with given prefix
     */
    public int howManyStartsWithPrefix(String prefix) {
        return head.howManyStartsWithPrefix(prefix);
    }

    /**
     * Removes element from trie.
     * @param element string to delete
     * @return true if trie contained element before removing, false otherwise
     */
    public boolean remove(String element) {
        return head.remove(element);
    }

    /**
     * Prints trie.
     * @param out output stream
     */
    public void serialize(OutputStream out) throws IOException {
        head.serialize(out);
    }

    /**
     * Reads trie.
     * @param in input stream
     */
    public void deserialize(InputStream in) throws IOException {
        head.deserialize(in);
    }

    /**
     * Vertex - vertices of which trie consists.
     */
    protected static class Vertex implements Serializable {
        private int size = 0;
        private boolean isTerminal = false;
        private Vertex[] next = new Vertex[Trie.ALPHABET_SIZE];

        /**
         * Getter for size.
         * @return quantity of stored strings with prefix ended in this vertex
         */
        protected int getSize() {
            return size;
        }

        /**
         * Adds new element starting from this vertex.
         * @param element new string to add
         * @return true if trie contained element before adding, false otherwise
         */
        protected boolean add(String element) {
            boolean existed;

            if (element.length() == 0) {
                existed = isTerminal;
                isTerminal = true;
                if (!existed) {
                    size++;
                }
            } else {
                Vertex nextVertex = next[element.charAt(0)];

                if (nextVertex == null) {
                    next[element.charAt(0)] = new Vertex();
                    nextVertex = next[element.charAt(0)];
                }

                size -= nextVertex.getSize();
                existed = nextVertex.add(element.substring(1));
                size += nextVertex.getSize();
            }

            return existed;
        }

        /**
         * Checks whether trie contains element.
         * @param element string to find in trie
         * @return true if trie contains element, false otherwise
         */
        protected boolean contains(String element) {
            boolean existed;

            if (element.length() == 0) {
                existed = isTerminal;
            } else {
                Vertex nextVertex = next[element.charAt(0)];
                existed = (nextVertex != null)
                        && nextVertex.contains(element.substring(1));
            }

            return existed;
        }

        /**
         * Counts quantity of stored strings with given prefix.
         * @param prefix common prefix to find
         * @return quantity of stored strings with given prefix
         */
        protected int howManyStartsWithPrefix(String prefix) {
            if (prefix.length() == 0) {
                return size;
            }

            Vertex nextVertex = next[prefix.charAt(0)];
            if (nextVertex == null) {
                return 0;
            }

            return nextVertex.howManyStartsWithPrefix(prefix.substring(1));
        }

        /**
         * Removes element from trie.
         * @param element string to delete
         * @return true if trie contained element before removing, false otherwise
         */
        protected boolean remove(String element) {
            boolean existed = false;

            if (element.length() == 0) {
                existed = isTerminal;
                isTerminal = false;
                if (existed) {
                    size--;
                }
            } else {
                Vertex nextVertex = next[element.charAt(0)];

                if (nextVertex != null) {
                    size -= nextVertex.getSize();
                    existed = nextVertex.remove(element.substring(1));
                    size += nextVertex.getSize();

                    if (nextVertex.getSize() == 0) {
                        next[element.charAt(0)] = null;
                    }
                }
            }

            return existed;
        }

        /**
         * Prints vertex.
         * @param out output stream
         */
        protected void serialize(OutputStream out) throws IOException {
            out.write(isTerminal ? 1 : 0);

            for (int i = 0; i < Trie.ALPHABET_SIZE; i++) {
                if (next[i] != null) {
                    out.write(ByteBuffer.allocate(4).putInt(i).array());
                    next[i].serialize(out);
                }
            }

            out.write(ByteBuffer.allocate(4).putInt(Trie.ALPHABET_SIZE + 1).array());
        }

        /**
         * Reads vertex.
         * @param in input stream
         */
        protected void deserialize(InputStream in) throws IOException {
            isTerminal = (in.read() == 1);
            if (isTerminal) {
                size++;
            }

            byte[] bytes = new byte[4];

            in.read(bytes);
            int i = ByteBuffer.wrap(bytes).getInt();
            while (i != Trie.ALPHABET_SIZE + 1) {
                next[i] = new Vertex();
                next[i].deserialize(in);
                size += next[i].getSize();

                in.read(bytes);
                i = ByteBuffer.wrap(bytes).getInt();
            }
        }
    }
}
