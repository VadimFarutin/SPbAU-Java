package ru.spbau.farutin.test2_03.network;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class for processing requests to server.
 */
public class Protocol {
    /**
     * Determines which command was requested and gives corresponding result.
     * @param input request, accepted commands:
     * 0 to quit
     * 1 <path> to list files
     * 2 <path> to get file content
     * @return result for the given request or message with error
     */
    public @NotNull String processInput(String input) {
        String output;

        if (input == null) {
            output = "\n  quit: 0\n  list: 1 <path>\n  get: 2 <path>";
        } else {
            switch (input.charAt(0)) {
                case '0':
                    output = "quit";
                    break;
                case '1':
                    output = list(input.substring(2));
                    break;
                case '2':
                    try {
                        output = get(input.substring(2));
                    } catch (IOException e) {
                        output = "failed reading file";
                    }
                    break;
                default:
                    output = "unknown command";
            }
        }

        return output;
    }

    /**
     * Gives directory content
     * @param path path to the directory
     * @return content of the given directory in the following format:
     * <size: Int> (<name: String> <is_dir: Boolean>)*
     */
    private @NotNull String list(@NotNull String path) {
        File root = new File(path);
        File[] files;

        if (!root.isDirectory() || (files = root.listFiles()) == null) {
            return "0\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(files.length);
        sb.append('\n');

        for (File file : files) {
            sb.append(file.getName());
            sb.append(' ');
            sb.append(file.isDirectory() ? "True" : "False");
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Gives file content
     * @param path path to the file
     * @return content of the given file in the following format:
     * <size: Long> <content: Bytes>
     * @throws IOException if any errors while reading file
     */
    private @NotNull String get(@NotNull String path) throws IOException {
        File file = new File(path);

        if (!file.exists()) {
            return "0\n";
        }

        FileInputStream stream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();

        byte[] buffer = new byte[1024];
        int c;
        long size = 0;

        while ((c = stream.read(buffer)) > 0) {
            sb.append(DatatypeConverter.printHexBinary(Arrays.copyOf(buffer, c)));
            size += c;
        }

        if (size != 0) {
            sb.append('\n');
        }

        sb.insert(0, '\n');
        sb.insert(0, size);

        return sb.toString();
    }
}
