package ru.job4j.concurrent.сas;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ru.job4j.concurrent.сas.CASCount;

class CASCountTest {

    @Test
    void when1000ThreadsThenCountIs1mln() throws InterruptedException {
        CASCount count = new CASCount(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    count.increment();
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertThat(threads.size()).isEqualTo(1000);
        assertThat(count.get()).isEqualTo(1000000);
    }
}
