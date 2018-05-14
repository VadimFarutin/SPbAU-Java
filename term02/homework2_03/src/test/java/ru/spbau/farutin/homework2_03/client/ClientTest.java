package ru.spbau.farutin.homework2_03.client;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.spbau.farutin.homework2_03.server.Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ClientTest {
    private static final int PORT_NUMBER = 4444;
    private static final String LOCAL_HOST = "127.0.0.1";

    private static Server server = new Server(PORT_NUMBER);
    private static Client client = new Client(LOCAL_HOST, PORT_NUMBER);

    @BeforeClass
    public static void setUp() throws Exception {
        Thread thread = new Thread(server);
        thread.start();
    }

    @Test
    public void testList() throws Exception {
        String input = "1 ./src/test/resources\n0";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        client.run(inputStream, outputStream);

        String output = new String(outputStream.toByteArray());
        String[] tokens = output.split("[\n\r]");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(tokens));
        Collections.sort(list);

        while (list.remove("")) {
        }

        String[] correct = {
                "  get: 2 <path>",
                "  list: 1 <path>",
                "  quit: 0",
                "Server: ",
                "Server: 5",
                "Server: quit",
                "dir1 True",
                "dir2 True",
                "file1.in False",
                "file2.out False",
                "file4.out False"};

        assertArrayEquals(list.toArray(), correct);
    }

    @Test
    public void testGet() throws Exception {
        String path = "./src/test/resources/file2.out";
        String input = "2 " + path + "\n0";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Client spyClient = spy(client);
        spyClient.run(inputStream, outputStream);

        verify(spyClient).save(path, "5\n48656C6C6F\n");
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }
}
