package ru.job4j.concurrent.Cas;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CASCountTest {

    @Test
    void when10000ThreadsThenCountIs100mln() throws InterruptedException {
        CASCount count = new CASCount(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
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
        assertThat(threads.size()).isEqualTo(10000);
        assertThat(count.get()).isEqualTo(100000000);
    }


}
