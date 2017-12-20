package ru.spbau.farutin.homework09;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;
import static ru.spbau.farutin.homework09.SecondPartTasks.*;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        assertTrue(
                "failed creating test files",
                createTestFiles());

        List<String> expected = Arrays.asList(ALL_CONTENT[0][2],
                                              ALL_CONTENT[0][4],
                                              ALL_CONTENT[1][2]);

        assertEquals(
                "wrong answer",
                expected,
                findQuotes(Arrays.asList(FILENAMES), QUOTE));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(
                "precision is not quite good",
                Math.PI / 4,
                piDividedBy4(),
                1e-3);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put(AUTHOR_0, COMPOSITIONS_0);
        compositions.put(AUTHOR_1, COMPOSITIONS_1);
        compositions.put(AUTHOR_2, COMPOSITIONS_2);

        assertEquals(
                "wrong answer",
                AUTHOR_1,
                findPrinter(compositions));

        assertTrue(
                "failed on empty map",
                findPrinter(Collections.emptyMap()) == null);
    }

    @Test
    public void testCalculateGlobalOrder() {
        Map<String, Integer> map0 = new HashMap<>();
        map0.put("1", 10);
        map0.put("2", 0);
        map0.put("3", 5);
        map0.put("4", 7);
        map0.put("5", 17);

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("2", 100);
        map1.put("3", 6);
        map1.put("4", 1);
        map1.put("6", 22);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("1", 10);
        map2.put("2", 0);
        map2.put("5", 3);
        map2.put("6", 42);

        Map<String, Integer> expected = new HashMap<>();
        expected.put("1", 20);
        expected.put("2", 100);
        expected.put("3", 11);
        expected.put("4", 8);
        expected.put("5", 20);
        expected.put("6", 64);

        assertEquals(
                "wrong answer",
                expected,
                calculateGlobalOrder(Arrays.asList(map0, map1, map2)));

        assertEquals(
                "failed on empty list",
                Collections.emptyMap(),
                calculateGlobalOrder(Collections.emptyList()));
    }

    @After
    public void clean() {
        File rootDirectory = new File(ROOT);
        File[] files = rootDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        rootDirectory.delete();
    }

    private boolean createTestFiles() {
        File rootDirectory = new File(ROOT);

        if (!rootDirectory.mkdir()) {
            return false;
        }

        for (int i = 0; i < FILENAMES.length; i++) {
            try (PrintWriter out = new PrintWriter(FILENAMES[i])) {
                for (String line : ALL_CONTENT[i]) {
                    out.println(line);
                }
            } catch (FileNotFoundException e) {
                return false;
            }
        }

        return true;
    }

    private static final String AUTHOR_0 = "Tolstoy";
    private static final String AUTHOR_1 = "Dostoevsky";
    private static final String AUTHOR_2 = "Pushkin";

    private static final List<String> COMPOSITIONS_0 = Arrays.asList("Well, Prince, so Genoa and Lucca are now just family estates of the Buonapartes.", "Happy families are all alike; every unhappy family is unhappy in its own way.", "Karl Ivanitch paid no attention to this rudeness, but went, as usual, with German politeness to kiss Mamma’s hand.");
    private static final List<String> COMPOSITIONS_1 = Arrays.asList("On an exceptionally hot evening early in July a young man came out of the garret in which he lodged in S. Place and walked slowly, as though in hesitation, towards K. bridge.", "Towards the end of November, during a thaw, at nine o’clock one morning, a train on the Warsaw and Petersburg railway was approaching the latter city at full speed. The morning was so damp and misty that it was only with great difficulty that the day succeeded in breaking; and it was impossible to distinguish anything more than a few yards away from the carriage windows.");
    private static final List<String> COMPOSITIONS_2 = Arrays.asList("My uncle -- high ideals inspire him; but when past joking he fell sick, he really forced one to admire him -- and never played a shrewder trick.", "My father, Andréj Petróvitch Grineff, after serving in his youth under Count Münich, had retired in 17—with the rank of senior major.");

    private static final String QUOTE = "hello";
    private static final String ROOT = Paths.get("").toAbsolutePath().toString() + "\\tmp";
    private static final String[] FILENAMES = { ROOT + "\\" + "srcfile0.txt",
                                                ROOT + "\\" + "srcfile1.txt",
                                                ROOT + "\\" + "srcfile2.txt" };

    private static final String[] CONTENT_0 = { "Hello", "world", "hello world", "content", "_hello_" };
    private static final String[] CONTENT_1 = { "sdjlafsldkj", "1234567890", "qwertyhellopoiu", "hell", "h", "e", "l", "l", "o" };
    private static final String[] CONTENT_2 = { "" };

    private static final String[][] ALL_CONTENT = { CONTENT_0, CONTENT_1, CONTENT_2 };
}