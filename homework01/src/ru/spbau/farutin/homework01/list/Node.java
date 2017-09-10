package ru.spbau.farutin.homework01.list;

/**
 * Node.java - вершина двусвязного списка ключей и значений типа String.
 * @author  Фарутин Вадим
 * @version 1.0
 */
public class Node {
    private String k;
    private String v;
    private Node n;
    private Node p;

    /**
     * Создание вершины с данным ключом и значением.
     * @param key ключ для поиска
     * @param value значение для данного ключа
     * @param next следующая вершина в списке
     * @param prev предыдущая вершина в списке
     */
    public Node(String key, String value, Node next, Node prev) {
        k = key;
        v = value;
        n = next;
        p = prev;
    }

    /**
     * Геттер для ключа.
     * @return ключ вершины
     */
    public String getKey() {
        return k;
    }

    /**
     * Геттер для значения.
     * @return значение вершины
     */
    public String getValue() {
        return v;
    }

    /**
     * Геттер для следующей вершины.
     * @return следующая вершина в списке
     */
    public Node getNext() {
        return n;
    }

    /**
     * Геттер для предыдущей вершины.
     * @return предыдущая вершина в списке
     */
    public Node getPrev() {
        return p;
    }

    /**
     * Сеттер для следующей вершины.
     * @param next следующая вершина в списке
     */
    public void setNext(Node next) {
        n = next;
    }

    /**
     * Сеттер для предыдущей вершины.
     * @param prev предыдущая вершина в списке
     */
    public void setPrev(Node prev) {
        p = prev;
    }
}
