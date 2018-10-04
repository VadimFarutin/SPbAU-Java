package ru.spbau.farutin.test2_01;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Console application to check directory md5 sum.
 * Counts sum twice - with on thread and using ForkJoinPool.
 */
public class MainApplication {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("expected format:");
            System.out.println("gradle run -Pargs=\"path\"");
            return;
        }

        String path = args[0];
        File root = new File(path);

        if (!root.isDirectory()) {
            System.out.println("directory not found: " + path);
            return;
        }

        DirectoryCheckSum checker = new DirectoryCheckSum();
        DirectoryCheckSumMultiThread checkerMulti = new DirectoryCheckSumMultiThread();

        try {
            long start = System.currentTimeMillis();

            byte[] checkSumSimple = checker.digest(root);
            System.out.printf("simple version results: %s in %5d msecs",
                    Arrays.toString(checkSumSimple),
                    System.currentTimeMillis() - start);

            start = System.currentTimeMillis();

            byte[] checkSumMulti = checkerMulti.digest(root);
            System.out.printf("fork join version results: %s in %5d msecs",
                    Arrays.toString(checkSumMulti),
                    System.currentTimeMillis() - start);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("failed to check : NoSuchAlgorithmException exception");
        } catch (IOException e) {
            System.out.println("failed to check : problems with file");
        }
   }
}
