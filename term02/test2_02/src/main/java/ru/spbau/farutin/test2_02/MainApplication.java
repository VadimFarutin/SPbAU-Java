package ru.spbau.farutin.test2_02;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Find pair application.
 */
public class MainApplication extends Application {
    private static int size;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("\nUsage: ./gradlew run -Parg1=<field_size>");
            return;
        }

        try {
            size = Integer.parseInt(args[0]);

            if ((size & 1) == 1) {
                System.err.println("\nEven size expected!");
                return;
            }

            if (size <= 0) {
                System.err.println("\nPositive size expected!");
                return;
            }

            Application.launch(MainApplication.class, args);
        } catch (NumberFormatException e) {
            System.err.println("\nWrong number format!");
        }
    }

    /**
     * Application launching.
     * @param stage initial stage
     * @throws IOException if failed to load file with scenes
     */
    @Override
    public void start(Stage stage) throws IOException {
        UI.initialize(stage);
        UI.showGameField();
    }

    public static int getSize() {
        return size;
    }
}
