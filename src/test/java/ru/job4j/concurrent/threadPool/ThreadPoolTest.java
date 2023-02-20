package ru.job4j.concurrent.threadPool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class ThreadPoolTest {

    @Test
    void when1000Increment() {
        ThreadPool threadPool = new ThreadPool();
        AtomicInteger atInt = new AtomicInteger();
        
        for (int i = 0; i < 1000; i++) {
            threadPool.work(() -> { 
                atInt.incrementAndGet();
            });
        }
        threadPool.waitTasks();
        threadPool.shutdown();
        assertThat(atInt.get()).isEqualTo(1000);
    }

}
