package ru.job4j.concurrent;

import ru.job4j.concurrent.threadPool.SimpleBlockingQueue;

public class ParallelSearch {
    
    public static void main(String[] args) {
        
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(1);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        
        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }

        ).start();
        
    }
}
