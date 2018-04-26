package ru.spbau.farutin.homework2_03.server;

import org.junit.Test;

import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Tests for Protocol class.
 */
public class ProtocolTest {
    /**
     * Checks initial protocol output.
     */
    @Test
    public void testProcessInputNull() throws Exception {
        Protocol protocol = new Protocol();

        assertThat(protocol.processInput(null), is("\n\tquit: 0\n\tlist: 1 <path>\n\tget: 2 <path>"));
    }

    /**
     * Checks protocol output for wrong command.
     */
    @Test
    public void testProcessInputUnknown() throws Exception {
        Protocol protocol = new Protocol();

        assertThat(protocol.processInput("3 ."), is("unknown command"));
    }

    /**
     * Checks protocol output for list command, directory exists.
     */
    @Test
    public void testProcessInputListExists() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("1 ./src/test/resources");
        String[] tokens = result.split("\n");
        Collections.sort(Arrays.asList(tokens));
        String[] correct = {"4", "dir1 True", "dir2 True", "file1.in False", "file2.out False"};

        assertArrayEquals(tokens, correct);
    }

    /**
     * Checks protocol output for list command, directory does not exist.
     */
    @Test
    public void testProcessInputListNotExists() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("1 ./src/test/resources/dir0");
        assertThat(result, is("0\n"));
    }

    /**
     * Checks protocol output for list command, directory is empty.
     */
    @Test
    public void testProcessInputListEmpty() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("1 ./src/test/resources/dir1");
        assertThat(result, is("0\n"));
    }

    /**
     * Checks protocol output for list command, file is not directory.
     */
    @Test
    public void testProcessInputListFile() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("1 ./src/test/resources/file1.in");
        assertThat(result, is("0\n"));
    }

    /**
     * Checks protocol output for get command, file exists.
     */
    @Test
    public void testProcessInputGetExists() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("2 ./src/test/resources/file2.out");
        assertThat(result, is("5\n48656C6C6F\n"));
    }

    /**
     * Checks protocol output for get command, file content does not fit buffer.
     */
    @Test
    public void testProcessInputGetBig() throws Exception {
        Protocol protocol = new Protocol();

        FileWriter writer = new FileWriter("./src/test/resources/file4.out");
        StringBuilder sb = new StringBuilder("2500\n");

        for (int i = 0; i < 500; i++) {
            writer.write("Hello");
            sb.append("48656C6C6F");
        }

        writer.close();
        sb.append('\n');

        String result = protocol.processInput("2 ./src/test/resources/file4.out");
        assertThat(result, is(sb.toString()));
    }

    /**
     * Checks protocol output for get command, file is empty.
     */
    @Test
    public void testProcessInputGetEmpty() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("2 ./src/test/resources/file1.in");
        assertThat(result, is("0\n"));
    }

    /**
     * Checks protocol output for get command, file doe not exist.
     */
    @Test
    public void testProcessInputGetNotExists() throws Exception {
        Protocol protocol = new Protocol();

        String result = protocol.processInput("2 ./src/test/resources/file0.in");
        assertThat(result, is("0\n"));
    }
}
