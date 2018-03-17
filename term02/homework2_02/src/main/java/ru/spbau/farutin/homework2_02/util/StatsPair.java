package ru.spbau.farutin.homework2_02.util;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * StatsPair - class representing properties pair to work with JavaFX TableView.
 */
public class StatsPair {
    private SimpleStringProperty key;
    private SimpleIntegerProperty value;

    public StatsPair(String key, int value) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleIntegerProperty(value);
    }

    public void setKey(String key) {
        this.key.set(key);
    }

    public void setValue(int value) {
        this.value.set(value);
    }

    public String getKey() {
        return key.get();
    }

    public int getValue() {
        return value.get();
    }
}
