package ru.job4j.concurrent.threadPool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class ThreadPoolTest {

    @Test
    void when1000Increment() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger atInt = new AtomicInteger();
        
        for (int i = 0; i < 1000; i++) {
            threadPool.work(() -> { 
                atInt.incrementAndGet();
            });
        }
        threadPool.shutdown();
        Thread.currentThread().sleep(20);
        assertThat(atInt.get()).isEqualTo(1000);
    }

}
