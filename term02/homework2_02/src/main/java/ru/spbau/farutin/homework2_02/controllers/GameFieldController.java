package ru.spbau.farutin.homework2_02.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ru.spbau.farutin.homework2_02.GameLogic;
import ru.spbau.farutin.homework2_02.UI;

/**
 * GameFieldController - controller for scene with game field.
 */
public class GameFieldController {
    @FXML
    private GridPane field;
    @FXML
    private Text status;

    /**
     * Sets up initial information.
     */
    @FXML
    public void initialize() {
        status.setText("X turn");
    }

    /**
     * Field buttons clicking handler.
     * @param event happened event
     */
    @FXML
    public void fieldButtonClick(ActionEvent event) throws Exception {
        javafx.scene.control.Button button = (Button) event.getSource();
        GameLogic.moveRequest(GridPane.getRowIndex(button), GridPane.getColumnIndex(button));
    }

    /**
     * Goes back in the menu hierarchy.
     */
    @FXML
    public void back() throws Exception {
        UI.newGame();
    }

    /**
     * Realizes player's move.
     * @param player mark type to fill the sell with
     * @param x first coordinate
     * @param y second coordinate
     */
    public void fillCell(int player, int x, int y) {
        ObservableList<Node> children = field.getChildren();

        for (Node button : children) {
            if (GridPane.getRowIndex(button) == x && GridPane.getColumnIndex(button) == y) {
                ((Button) button).setText(player == 0 ? "X" : "O");
                break;
            }
        }
    }

    /**
     * Changes information about players turn.
     * @param player current player
     */
    public void playerTurn(int player) {
        status.setText(player == 0 ? "X turn" : "O turn");
    }

    /**
     * Sets information about game results.
     * @param player winner, or -1 for draw
     */
    public void playerWin(int player) {
        String text;

        if (player == -1) {
            text = "Draw";
        } else if (player == 0) {
            text = "X win";
        } else {
            text = "O win";
        }

        status.setText(text);
    }
}
