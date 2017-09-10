package ru.spbau.farutin.homework01.list;

/**
 * List.java - двусвязный список ключей и значений типа String.
 * @author  Фарутин Вадим
 * @version 1.0
 */
public class List {
    private Node head;

    /**
     * Создание пустого списка.
     */
    public List() {
        head = null;
    }

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
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.getNext();
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
            if (current.getKey().equals(key)) {
                Node next = current.getNext();
                Node prev = current.getPrev();

                if (next != null) {
                    next.setPrev(prev);
                }
                if (prev != null) {
                    prev.setNext(next);
                }

                return current.getValue();
            }
            current = current.getNext();
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
}
