package ru.spbau.farutin.test01.list;

/**
 * List.java - двусвязный список.
 */
public class List<K, V> {
    private Node<K, V> head = null;

    /**
     * Добавление пары.
     * @param key ключ
     * @param value новое значение для данного ключа
     */
    public void add(K key, V value) {
        head = new Node<K, V>(key, value, head, null);
    }

    /**
     * Поиск значения по ключу.
     * @param key ключ для поиска
     * @return значение по ключу, или null, если такого значения нет
     */
    public V find(Object key) {
        Node<K, V> current = head;

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
    public V remove(Object key) {
        Node<K, V> current = head;

        while (current != null) {
            if (current.key.equals(key)) {
                Node<K, V> next = current.next;
                Node<K, V> prev = current.prev;

                if (next != null) {
                    next.prev = prev;
                }
                if (prev != null) {
                    prev.next = next;
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
    public Node<K, V> getHead() {
        return head;
    }

    /**
     * Возвращает ключ первого элемента.
     * @return ключ первого элемента
     */
    public K getHeadKey() {
        return head.key;
    }

    /**
     * Возвращает значение первого элемента.
     * @return значение первого элемента
     */
    public V getHeadValue() {
        return head.value;
    }

    /**
     * Удаляет первый элемент.
     */
    public void removeHead() {
        head = head.next;
    }

    /**
     * Node.java - вершина двусвязного списка.
     */
    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;
        private Node<K, V> prev;

        /**
         * Создание вершины с данным ключом и значением.
         * @param newKey ключ для поиска
         * @param newValue значение для данного ключа
         * @param newNext следующая вершина в списке
         * @param newPrev предыдущая вершина в списке
         */
        private Node(K newKey, V newValue, Node<K, V> newNext, Node<K, V> newPrev) {
            key = newKey;
            value = newValue;
            next = newNext;
            prev = newPrev;
        }
    }
}
