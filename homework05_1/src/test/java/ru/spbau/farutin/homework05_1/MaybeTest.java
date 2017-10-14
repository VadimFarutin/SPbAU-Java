package ru.spbau.farutin.homework05_1;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * MaybeTest - test class for Maybe.
 */
public class MaybeTest {
    /**
     * Tests creating Maybe with not null value.
     */
    @Test
    public void testJust() {
        Maybe<Integer> maybe = Maybe.just(7);

        assertEquals("should be not null instance", true, maybe.isPresent());
    }

    /**
     * Tests creating Maybe with null value.
     */
    @Test
    public void testNothing() {
        Maybe<Integer> maybe = Maybe.nothing();

        assertEquals("should be null instance", false, maybe.isPresent());
    }

    /**
     * Tests get() on Maybe with not null value.
     */
    @Test
    public void testGetNotNull() {
        Maybe<Integer> maybe = Maybe.just(7);

        try {
            int i = maybe.get();
            assertEquals("wrong value", 7, i);
            assertTrue(e.getMessage(), false);
        } catch (ValueNotFoundException e) {
        }
    }

    /**
     * Tests get() on Maybe with null value.
     */
    @Test
    public void testGetNull() {
        Maybe<Integer> maybe = Maybe.nothing();

        try {
            int i = maybe.get();
            assertTrue("get() did not throw an exception", false);
        } catch (MaybeException e) {
            assertEquals("wrong exception","Get from nothing", e.getMessage());
        }
    }

    /**
     * Tests isPresent() on Maybe with not null value.
     */
    @Test
    public void testIsPresentNotNull() {
        Maybe<Integer> maybe = Maybe.just(7);

        assertEquals("should be not null instance", true, maybe.isPresent());
    }

    /**
     * Tests isPresent() on Maybe with null value.
     */
    @Test
    public void testIsPresentNull() {
        Maybe<Integer> maybe = Maybe.nothing();

        assertEquals("should be null instance", false, maybe.isPresent());
    }

    /**
     * Tests map() on Maybe with not null value.
     */
    @Test
    public void testMapNotNull() {
        Maybe<Integer> maybe = Maybe.just(7);
        Maybe<Integer> applied = maybe.map(x -> x * 2);

        try {
            int i = applied.get();
            assertEquals("wrong value", 14, i);
            assertTrue(e.getMessage(), false);
        } catch (ValueNotFoundException e) {
        }
    }

    /**
     * Tests map() on Maybe with null value.
     */
    @Test
    public void testMapNull() {
        Maybe<Integer> maybe = Maybe.nothing();
        Maybe<Integer> applied = maybe.map(x -> x * 2);

        assertEquals("should be null instance", false, applied.isPresent());
    }

    /**
     * Reads numbers from every line in test file input.txt.
     */
    @Test
    public void testReadFromFile() {
        File input = new File("input.txt");

        try {
            Scanner scanner = new Scanner(input);
            ArrayList<Maybe<Integer>> expected = new ArrayList<>();
            expected.add(Maybe.just(123));
            expected.add(Maybe.just(0));
            expected.add(Maybe.nothing());
            expected.add(Maybe.just(477));
            expected.add(Maybe.nothing());
            expected.add(Maybe.nothing());

            ArrayList<Maybe<Integer>> result = new ArrayList<>();

            while (scanner.hasNextLine()) {
                result.add(readInt(scanner));
            }

            assertEquals("wrong array size", expected.size(), result.size());
            for (int i = 0; i < expected.size(); i++) {
                if (expected.get(i).isPresent()) {
                    assertEquals("element should not be null",
                            true, result.get(i).isPresent());
                    try {
                        assertEquals("wrong number",
                                expected.get(i).get(), result.get(i).get());
                        assertTrue(e.getMessage(), false);
                    } catch (ValueNotFoundException e) {
                    }
                } else {
                    assertEquals("element should be null",
                            false, result.get(i).isPresent());
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            assertTrue(e.getMessage(), false);
        }
    }

    /**
     * Reads number from one line.
     * @return Maybe with nothing, if line was not a nubmer,
     * Maybe with integer otherwise.
     */
    private @NotNull Maybe<Integer> readInt(@NotNull Scanner scanner) {
        String line = scanner.nextLine();
        Maybe<Integer> maybe;

        try {
            maybe = Maybe.just(Integer.parseInt(line));
        } catch (NumberFormatException e) {
            maybe = Maybe.nothing();
        }

        return maybe;
    }

    /**
     * Reads numbers from input.txt and writes their squares to output.txt
     * or string "null" if corresponding Maybe stores nothing.
     */
    @Test
    public void testWriteFile() {
        File input = new File("input.txt");
        Path output = Paths.get("output.txt");

        try {
            Scanner scanner = new Scanner(input);
            BufferedWriter out = Files.newBufferedWriter(output);

            ArrayList<Maybe<Integer>> result = new ArrayList<>();

            while (scanner.hasNextLine()) {
                result.add(readInt(scanner));
            }

            for (int i = 0; i < result.size(); i++) {
                if (result.get(i).isPresent()) {
                    try {
                        int a = result.get(i).get();
                        out.write(String.valueOf(a * a));
                        out.newLine();
                        assertTrue(e.getMessage(), false);
                    } catch (ValueNotFoundException e) {
                    }
                } else {
                    out.write("null");
                    out.newLine();
                }
            }

            scanner.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            assertTrue(e.getMessage(), false);
        }
    }
}