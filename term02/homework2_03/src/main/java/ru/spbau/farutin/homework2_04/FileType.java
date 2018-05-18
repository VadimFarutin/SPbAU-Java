package ru.spbau.farutin.homework2_04;

/**
 * Enum for types of files.
 */
public enum FileType {
    DIRECTORY,
    FILE;

    /**
     * Casts boolean to FileType.
     * @param isDirectory value to cast
     * @return built FileType depending on given value
     */
    public static FileType valueOf(boolean isDirectory) {
        return isDirectory ? DIRECTORY : FILE;
    }
}
