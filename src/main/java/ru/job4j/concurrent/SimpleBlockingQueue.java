package ru.job4j.concurrent;

import java.util.LinkedList;
import java.util.Queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    
    private int count;       

    public SimpleBlockingQueue(int count) {
        this.count = count;
    }
    
    private synchronized void waitOrInterrupt() {
        try {
            this.wait();
        } catch (InterruptedException e) {                
            Thread.currentThread().interrupt();
        }
    }
    
    public synchronized void offer(T value) {
        while (queue.size() >= count) {
            waitOrInterrupt();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() {
        while (queue.peek() == null) {
            waitOrInterrupt();
        }
        this.notifyAll();
        return queue.poll();        
    }

}
