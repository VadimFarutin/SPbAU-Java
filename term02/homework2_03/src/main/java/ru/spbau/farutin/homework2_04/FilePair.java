package ru.spbau.farutin.homework2_04;

import javafx.beans.property.SimpleStringProperty;

/**
 * FilePair - class representing file properties for TreeTable.
 */
public class FilePair {
    private SimpleStringProperty name;
    private SimpleStringProperty type;

    public FilePair(String name, String type) {
        this.name = new SimpleStringProperty(name);
        this.type = new SimpleStringProperty(type);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getName() {
        return name.get();
    }

    public String getType() {
        return type.get();
    }
}
