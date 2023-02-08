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
        count.incrementAndGet();
    }

    public int get() {
        return count.get();
    }

}
