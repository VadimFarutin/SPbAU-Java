package ru.spbau.farutin.homework01;

import ru.spbau.farutin.homework01.list.*;

/**
 * HashTable.java - хеш-таблица с ключами и значениями типа String.
 * @author  Фарутин Вадим
 * @version 1.0
 */
public class HashTable {
    private int size;
    private List[] data;

    /**
     * Создание пустой хеш-таблицы.
     */
    public HashTable() {
        size = 0;
        data = new List[] {new List()};
    }

    /**
     * Возвращает количество ключей в хеш-таблице.
     * @return количество ключей
     */
    public int size() {
        return size;
    }

    /**
     * Проверка наличия данного ключа в хеш-таблице.
     * @param key ключ для поиска
     * @return true, если данный ключ содержится в хеш-таблице, false иначе
     */
    public boolean contains(String key) {
        return data[hash(key)].find(key) != null;
    }

    /**
     * Возвращает пару для данного ключа.
     * @param key ключ для поиска
     * @return значение по ключу, или null, если такого значения нет
     */
    public String get(String key) {
        return data[hash(key)].find(key);
    }

    /**
     * Кладет в хеш-таблицу значение value по ключу key.
     * @param key ключ для поиска
     * @param value новое значение для данного ключа
     * @return старое значение по ключу, или null, если такого значения нет
     */
    public String put(String key, String value) {
        String oldValue = data[hash(key)].remove(key);

        if (oldValue == null) {
            size++;
        }
        data[hash(key)].add(key, value);
        if (size == data.length) {
            resize();
        }

        return oldValue;
    }

    /**
     * Удаление значения по заданному ключу из хеш-таблицы.
     * @param key ключ для поиска
     * @return удалённое значение, либо null, если такого значения не было
     */
    public String remove(String key) {
        String value = data[hash(key)].remove(key);

        if (value != null) {
            size--;
        }

        return value;
    }

    /**
     * Очистить хеш-таблицу.
     */
    public void clear() {
        size = 0;

        for (int i = 0; i < data.length; i++) {
            data[i].clear();
        }
    }

    /**
     * Считает значение хеш-функции для данного ключа.
     * @param key ключ для расчета
     * @return значение хеш-функции для данного ключа
     */
    private int hash(String key) {
        return Math.floorMod(key.hashCode(), data.length);
    }

    /**
     * Увеличение размера хеш-таблицы в два раза.
     */
    private void resize() {
        size = 0;
        List[] oldData = data;
        data = new List[oldData.length * 2];

        for (int i = 0; i < data.length; i++) {
            data[i] = new List();
        }

        for (int i = 0; i < oldData.length; i++) {
            Node current = oldData[i].getHead();
            while (current != null) {
                put(current.getKey(), current.getValue());
                current = current.getNext();
            }
        }
    }
}
