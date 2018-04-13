package ru.spbau.farutin.test2_01;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Checks directory md5 sum.
 */
public class DirectoryCheckSum {
    private static int BUFFER_SIZE = 1024;

    /**
     * Checks file md5 sum using formula:
     * f(file) = MD5(file)
     * f(dir) = MD5(dir_name + f(file1) + ...)
     *
     * @param root root file
     * @return md5 sum
     * @throws NoSuchAlgorithmException if failed to create md5
     * @throws IOException error while working with files
     */
    public @NotNull byte[] digest(@NotNull File root) throws
            NoSuchAlgorithmException,
            IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");

        if (!root.isDirectory()) {
            try (FileInputStream fileInputStream = new FileInputStream(root)) {
                DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, md);
                byte[] buffer = new byte[BUFFER_SIZE];

                while (digestInputStream.read(buffer) > -1) {
                }

                return digestInputStream.getMessageDigest().digest();
            }
        }

        md.update(root.getName().getBytes());
        File[] filesArray = root.listFiles();

        if (filesArray != null) {
            List<File> filesList = Arrays.asList(filesArray);
            Collections.sort(filesList);

            for (File file : filesList) {
                md.update(digest(file));
            }
        }

        return md.digest();
    }
}
