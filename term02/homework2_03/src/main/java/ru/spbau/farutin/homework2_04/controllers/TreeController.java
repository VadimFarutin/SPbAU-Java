package ru.spbau.farutin.homework2_04.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.homework2_04.Client;
import ru.spbau.farutin.homework2_04.FilePair;
import ru.spbau.farutin.homework2_04.FileType;
import ru.spbau.farutin.homework2_04.UI;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

/**
 * TreeController - controller for scene with tree.
 */
public class TreeController {
    private Stack<String> roots = new Stack<>();

    @FXML
    private Text currentRoot;
    @FXML
    private TableView<FilePair> treeTable;

    /**
     * Lists root directory.
     */
    @FXML
    public void initialize() {
        roots.push(".");
        list(roots.peek());
    }

    /**
     * Handles clicking on table.
     */
    @FXML
    public void onTableClick() {
        FilePair clicked = treeTable.getSelectionModel().getSelectedItem();

        if (clicked == null) {
            return;
        }

        String fullPath = roots.peek() + "/" + clicked.getName();

        if (clicked.getName().equals("..")) {
            back();
            return;
        }

        if (FileType.valueOf(clicked.getType()) == FileType.DIRECTORY) {
            roots.push(fullPath);
            list(roots.peek());
        } else {
            UI.showOneButtonNotification("Save file " + fullPath + "?",
                                         "Save",
                                         event -> get(fullPath));
        }
    }

    /**
     * Handles keyboard actions.
     * @param event event with pressed key
     */
    @FXML
    public void onKeyPressed(javafx.scene.input.KeyEvent event) {
        javafx.scene.input.KeyCode eventCode = event.getCode();

        if (eventCode.equals(KeyCode.ENTER)) {
            onTableClick();
        } else if (eventCode.equals(KeyCode.BACK_SPACE)) {
            back();
        }
    }

    /**
     * Goes up in the hierarchy.
     */
    @FXML
    public void back() {
        if (roots.size() > 1) {
            roots.pop();
            list(roots.peek());
        }
    }

    /**
     * Closes current connection.
     * @throws IOException if failed to close
     */
    @FXML
    public void disconnect() throws IOException {
        try {
            Client.getInstance().disconnect();
        } catch (IOException e) {
            UI.showNotification("Failed closing connection.");
            return;
        }

        UI.showMainMenu();
    }

    private void list(@NotNull String path) {
        try {
            String result = Client.getInstance().list(path);
            String[] tokens = result.split("[\n ]");

            ObservableList<FilePair> data = FXCollections.observableArrayList();

            if (!path.equals(".")) {
                data.add(new FilePair("..",
                                      FileType.valueOf(true).name()));
            }

            for (int i = 1; i < tokens.length; i += 2) {
                data.add(new FilePair(tokens[i],
                                      FileType.valueOf(Boolean.parseBoolean(tokens[i + 1])).name()));
            }

            treeTable.setItems(data);
            currentRoot.setText(path);
        } catch (IOException e) {
            UI.showNotification("Failed reading from server.");
            roots.pop();
        }
    }

    private void get(@NotNull String path) {
        try {
            String result = Client.getInstance().get(path);
            String bytes = result.split("\n")[1];
            byte[] data = DatatypeConverter.parseHexBinary(bytes);

            File file = new File(path);
            File parent = file.getParentFile();
            if (!parent.getName().equals(".") && !parent.mkdirs()) {
                throw new IOException("failed to create directories");
            }

            try (FileOutputStream output = new FileOutputStream(path)) {
                output.write(data);
            }
        } catch (IOException e) {
            UI.showNotification("Failed reading from server.");
        }
    }
}
