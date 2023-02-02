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
    
    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() >= count) {
            this.wait();
        }
        queue.add(value);
        this.notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.peek() == null) {
            this.wait();
        }
        this.notifyAll();
        return queue.poll();        
    }

}