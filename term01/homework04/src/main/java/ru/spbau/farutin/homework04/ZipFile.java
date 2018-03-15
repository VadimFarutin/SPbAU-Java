package ru.spbau.farutin.homework04;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * ZipFile.java -- extracts files matching given pattern
 * from all zip-archives in a directory.
 */
public class ZipFile {
    private static final int BUFFER_SIZE = 1024;
    private ArrayList<String> archives = new ArrayList<>();

    /**
     * Searches and extracts all files matching given pattern
     * from zip-archives in a directory.
     * @param path directory with zip-archives
     * @param regex pattern to search
     * @return true if path exists, false otherwise
     */
    public boolean extract(@NotNull String path, @NotNull String regex) {
        File root = new File(path);

        if (!root.isDirectory()) {
            return false;
        }

        findArchives(root);
        findAndUnzip(regex);

        return true;
    }

    /**
     * Searches recursively zip-archives in a directory.
     * @param root directory with zip-archives
     */
    private void findArchives(@NotNull File root) {
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                findArchives(file);
            } else if (file.getName().endsWith(".zip")) {
                archives.add(file.getAbsolutePath());
            }
        }
    }

    /**
     * Extracts files matching given pattern from zip-archives.
     * @param regex pattern to search
     */
    private void findAndUnzip(@NotNull String regex) {
        byte[] buffer = new byte[BUFFER_SIZE];

        for (String file : archives) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
                ZipEntry entry;

                while ((entry = zipInputStream.getNextEntry()) != null) {
                    Path absolutePath = Paths.get(entry.getName());

                    if (absolutePath.getFileName().toString().matches(regex)) {
                        OutputStream out = new FileOutputStream(absolutePath.toString());

                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            out.write(buffer, 0, length);
                        }

                        out.close();
                    }

                    zipInputStream.closeEntry();
                }

                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
