package ru.job4j.concurrent.сas;

import java.util.concurrent.atomic.AtomicInteger;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count;

    public CASCount(int count) {
        this.count = new AtomicInteger(count);
    }

    public void increment() {
        int expectedValue;
        int updateValue;
        do {
            expectedValue = count.get();
            updateValue = expectedValue + 1;
        } while (!count.compareAndSet(expectedValue, updateValue));
    }

    public int get() {
        return count.get();
    }

}
