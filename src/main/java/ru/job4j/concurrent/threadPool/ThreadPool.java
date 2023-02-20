package ru.job4j.concurrent.threadPool;

import java.util.LinkedList;
import java.util.List;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class ThreadPool {
    
    int size;   
    
    @GuardedBy("this")
    private final SimpleBlockingQueue<Runnable> tasks;
    
    private final List<Thread> threads = new LinkedList<>();    

    public ThreadPool() {
        size = Runtime.getRuntime().availableProcessors();
        tasks = new SimpleBlockingQueue<>(size);
        
        for (int i = 0; i < size; i++) {
            threads.add(new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Runnable task = tasks.poll();
                        task.run();
                    } catch (InterruptedException e) {
                    }
                }
            }));
        }
        
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public synchronized void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
        }
    }
    
    public synchronized void waitTasks() {
        while (!tasks.isEmpty()) {
            try {
                Thread.currentThread().sleep(10);
            } catch (InterruptedException e) {
            }
        }        
    }

    public synchronized void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();   
        }        
    }

}
