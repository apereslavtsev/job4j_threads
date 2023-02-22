package ru.job4j.concurrent.threadPool;

import java.util.LinkedList;
import java.util.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ThreadPool {
    
    private int size = Runtime.getRuntime().availableProcessors();   
    
    @GuardedBy("this")
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);
    
    private final List<Thread> threads = new LinkedList<>();    

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();   
        }        
    }

}