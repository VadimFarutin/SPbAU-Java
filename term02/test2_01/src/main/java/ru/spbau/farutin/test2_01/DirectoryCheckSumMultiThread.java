package ru.spbau.farutin.test2_01;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Checks directory md5 sum using ForkJoinPool.
 */
public class DirectoryCheckSumMultiThread {
    private static int BUFFER_SIZE = 1024;
    private ForkJoinPool pool = new ForkJoinPool(4);

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
        RecursiveDigest task = new RecursiveDigest(root, md);

        return pool.invoke(task);
    }

    /**
     * Task for ForkJoinPool.
     */
    private static class RecursiveDigest extends RecursiveTask<byte[]> {
        private File root;
        private MessageDigest md;

        RecursiveDigest(@NotNull File file, @NotNull MessageDigest md){
            this.root = file;
            this.md = md;
        }

        /**
         * Checks file md5 sum using formula:
         * f(file) = MD5(file)
         * f(dir) = MD5(dir_name + f(file1) + ...)
         *
         * @return md5 sum
         */
        @Override
        protected byte[] compute() {
            if (!root.isDirectory()) {
                try (FileInputStream fileInputStream = new FileInputStream(root)) {
                    DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, md);
                    byte[] buffer = new byte[BUFFER_SIZE];

                    while (digestInputStream.read(buffer) > -1) {
                    }

                    return digestInputStream.getMessageDigest().digest();
                } catch (IOException e) {
                    completeExceptionally(e);
                }
            }

            md.update(root.getName().getBytes());
            File[] filesArray = root.listFiles();

            if (filesArray != null) {
                List<File> filesList = Arrays.asList(filesArray);
                Collections.sort(filesList);

                List<RecursiveDigest> tasks = new ArrayList<>();

                for (File file : filesList) {
                    tasks.add(new RecursiveDigest(file, md));
                }

                for (RecursiveDigest task : tasks) {
                    task.fork();
                }

                for (RecursiveDigest task : tasks) {
                    md.update(task.join());

                    if (task.getException() != null) {
                        completeExceptionally(task.getException());
                    }
                }
            }

            return md.digest();
        }
    }
}
