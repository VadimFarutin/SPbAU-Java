package ru.spbau.farutin.homework2_05;

/**
 * Exception is thrown when there is illegal annotations combination in the test suite.
 */
public class IllegalTestSuiteConfigurationException extends Exception {
    public IllegalTestSuiteConfigurationException(String message) {
        super(message);
    }
}
