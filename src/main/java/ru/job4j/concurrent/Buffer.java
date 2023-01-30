package ru.job4j.concurrent;

public class Buffer {

    private StringBuilder buffer = new StringBuilder();

    /**
     * объект класса - монитор код метода - критическая секция
     */
    public synchronized void add(int value) {
        /* код метода - критическая секция */
        System.out.print(value);
        buffer.append(value);
    }

    /**
     * объект класса - монитор код метода - критическая секция
     */
    @Override
    public synchronized String toString() {
        /* код метода - критическая секция */
        return buffer.toString();
    }

    public void sout() {
        if (!buffer.isEmpty()) {
            /* this - объект класса - монитор */
            synchronized (this) {
                /* код внутри блока synchronized(this) - критическая секция */
                System.out.println(toString());
            }
        }
    }

}
