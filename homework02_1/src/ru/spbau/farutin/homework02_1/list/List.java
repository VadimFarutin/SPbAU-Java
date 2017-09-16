package ru.spbau.farutin.homework02_1.list;

/**
 * List.java - двусвязный список ключей и значений типа String.
 */
public class List {
    protected Node head = null;

    /**
     * Добавление пары.
     * @param key ключ
     * @param value новое значение для данного ключа
     */
    public void add(String key, String value) {
        head = new Node(key, value, head, null);
    }

    /**
     * Поиск значения по ключу.
     * @param key ключ для поиска
     * @return значение по ключу, или null, если такого значения нет
     */
    public String find(String key) {
        Node current = head;

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Удаление значения по ключу.
     * @param key ключ для поиска
     * @return удалённое значение, либо null, если такого значения не было
     */
    public String remove(String key) {
        Node current = head;

        while (current != null) {
            if (current.key.equals(key)) {
                Node next = current.next;
                Node prev = current.prev;

                if (next != null) {
                    next.prev = prev;
                }
                if (prev != null) {
                    prev.next = next;
                }

                if (next == null && prev == null) {
                    head = null;
                }

                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    /**
     * Очистить список.
     */
    public void clear() {
        head = null;
    }

    /**
     * Получение первого элемента списка.
     * @return первый элемент списка
     */
    public Node getHead() {
        return head;
    }


    /**
     * Node.java - вершина двусвязного списка ключей и значений типа String.
     */
    public static class Node {
        protected String key;
        protected String value;
        protected Node next;
        protected Node prev;

        /**
         * Создание вершины с данным ключом и значением.
         * @param newKey ключ для поиска
         * @param newValue значение для данного ключа
         * @param newNext следующая вершина в списке
         * @param newPrev предыдущая вершина в списке
         */
        protected Node(String newKey, String newValue, Node newNext, Node newPrev) {
            key = newKey;
            value = newValue;
            next = newNext;
            prev = newPrev;
        }

        protected Node(){}

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public Node getNext() {
            return next;
        }
    }
}
