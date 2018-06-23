package ru.spbau.farutin.test2_03.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ru.spbau.farutin.test2_03.util.Stats;
import ru.spbau.farutin.test2_03.util.StatsPair;
import ru.spbau.farutin.test2_03.UI;

import java.io.IOException;

/**
 * StatsController - controller for scene with stats.
 */
public class StatsController {
    @FXML
    private TableView<StatsPair> statsTable;

    /**
     * Fills stats table with saved data.
     */
    @FXML
    public void initialize() {
        ObservableList<StatsPair> data = FXCollections.observableArrayList(
                new StatsPair("win", Stats.getWinCnt()),
                new StatsPair("draw", Stats.getDrawCnt()),
                new StatsPair("lose", Stats.getLoseCnt()));

        statsTable.setItems(data);
    }

    /**
     * Goes back in the menu hierarchy.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void goBack() throws IOException {
        UI.showMainMenu();
    }
}
