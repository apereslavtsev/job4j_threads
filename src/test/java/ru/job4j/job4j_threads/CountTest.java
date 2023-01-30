package ru.job4j.job4j_threads;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ru.job4j.concurrent.Count;

class CountTest {
    
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        var count = new Count();
        var first = new Thread(count::increment);
        var second = new Thread(count::increment);
        /* Запускаем нити. */
        first.start();
        second.start();
        /* Заставляем главную нить дождаться выполнения наших нитей. */
        first.join();
        second.join();
        /* Проверяем результат. */
        assertThat(count.get()).isEqualTo(2);
    }

}
