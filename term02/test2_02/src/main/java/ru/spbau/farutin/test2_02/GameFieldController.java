package ru.spbau.farutin.test2_02;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * GameFieldController - controller for scene with game field.
 */
public class GameFieldController {
    @FXML
    private GridPane field;
    @FXML
    private Text status;

    private Button[][] buttons;
    private GameLogic logic;

    /**
     * Sets up initial information.
     */
    @FXML
    public void initialize() {
        status.setText("Find all pairs!");
        logic = new GameLogic(this, MainApplication.getSize());
        buildField();
    }

    /**
     * Starts new game.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void newGame() throws IOException {
        UI.showGameField();
    }

    private void buildField() {
        int size = MainApplication.getSize();
        buttons = new Button[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                int finalI = i;
                int finalJ = j;

                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(20);
                buttons[i][j].setMinHeight(20);
                buttons[i][j].setOnAction(event -> {
                    logic.clickButton(finalI, finalJ);
                });

                field.add(buttons[i][j], i, j);
            }
        }
    }

    /**
     * Getter for buttons.
     * @param i first coordinate
     * @param j second coordinate
     * @return button with given coordinates
     */
    public Button getButton(int i, int j) {
        return buttons[i][j];
    }

    /**
     * Display message.
     * @param message message to show
     */
    public void setStatus(@NotNull String message) {
        status.setText(message);
    }
}
