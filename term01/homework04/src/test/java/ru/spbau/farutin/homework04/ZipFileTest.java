package ru.spbau.farutin.homework04;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

/**
 * ZipFileTest.java -- test for ZipFile.
 */
public class ZipFileTest {
    private static final int BUFFER_SIZE = 1024;
    private static final int MAX_FILE_SIZE = 102400;
    private static String root = Paths.get("").toAbsolutePath().toString() + "\\tmp";
    private static String regex = "src(.*).txt";
    private static String[] testFiles = { "srcfile1.txt",
                                         "srcfile2.txt",
                                         "srcfile3.txt",
                                         "hello.txt",
                                         "srcfile4.txt",
                                         "srcfile5.txt",
                                         "world.txt" };

    /**
     * Deletes temporary directory and extracted files.
     */
    @After
    public void clean() {
        deleteDirectory(root + "\\subdirectory");
        deleteDirectory(root);

        for (int i = 0; i < testFiles.length; i++) {
            File file = new File(testFiles[i]);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * Deletes all files in given directory and directory itself.
     * @param root path to directory
     */
    private void deleteDirectory(String root) {
        File rootDirectory = new File(root);
        File[] files = rootDirectory.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].exists()) {
                    files[i].delete();
                }
            }
        }

        rootDirectory.delete();
    }

    /**
     * Generates temporary archive and extracts it.
     */
    @Test
    public void testExtract() {
        boolean isDirectoryCreated = createArchives();

        assertTrue("failed creating test directory", isDirectoryCreated);

        ZipFile zipFile = new ZipFile();
        boolean isRootFound = zipFile.extract(root, regex);

        assertTrue("root directory not found", isRootFound);

        checkFiles();
    }

    /**
     * Creates archives in root directory.
     * @return true if directories were created, false otherwise
     */
    private boolean createArchives() {
        File rootDirectory = new File(root);
        if (!rootDirectory.mkdir()) {
            return false;
        }

        String zipFile = root + "\\archive1.zip";
        String[] srcFiles = new String[4];
        for (int i = 0; i < srcFiles.length; i++) {
            srcFiles[i] = root + "\\" + testFiles[i];
        }

        archiveRandomFiles(zipFile, srcFiles);

        File rootSubdirectory = new File(root + "\\subdirectory");
        if (!rootSubdirectory.mkdir()) {
            return false;
        }

        String zipFileSubdirectory = root + "\\subdirectory\\archive2.zip";
        String[] srcFilesSubdirectory = new String[3];
        for (int i = 0; i < srcFilesSubdirectory.length; i++) {
            srcFilesSubdirectory[i] = root + "\\subdirectory\\" + testFiles[srcFiles.length + i];
        }

        archiveRandomFiles(zipFileSubdirectory, srcFilesSubdirectory);

        return true;
    }

    /**
     * Generates random files with given names and puts them into archive.
     * @param zipFile zip archive that files will be add to
     * @param srcFiles names for new files
     */
    private void archiveRandomFiles(String zipFile, String[] srcFiles) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            FileOutputStream zipFileOutputStream = new FileOutputStream(zipFile);
            ZipOutputStream out = new ZipOutputStream(zipFileOutputStream);

            for (int i = 0; i < srcFiles.length; i++) {
                File srcFile = new File(srcFiles[i]);

                generateRandomFile(srcFile);

                FileInputStream in = new FileInputStream(srcFile);
                out.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                out.closeEntry();
                in.close();
            }

            out.close();
            zipFileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills file with random bytes.
     * @param srcFile filename
     */
    private void generateRandomFile(File srcFile) {
        Random random = new Random();

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(srcFile);
            byte[] randomBytes = new byte[random.nextInt(MAX_FILE_SIZE)];

            random.nextBytes(randomBytes);

            fileOutputStream.write(randomBytes);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether all files were extracted or not.
     */
    private void checkFiles() {
        for (int i = 0; i < testFiles.length; i++) {
            if (testFiles[i].matches(regex)) {
                assertTrue("file not found", new File(testFiles[i]).exists());
            }
        }
    }
}