import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import ru.job4j.concurrent.SimpleBlockingQueue;

class SimpleBlockingQueueTest {

    @Test
    void when3ConsumerAnd3Producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> block = new SimpleBlockingQueue<>(1);
        List<Integer> buffer = new CopyOnWriteArrayList<Integer>();

        Runnable produser = new Runnable() {
            @Override
            public void run() {
                try {
                    block.offer(Integer.valueOf(Thread.currentThread().getName()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    buffer.add(block.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread consumer1 = new Thread(consumer, "consumer1");
        consumer1.start();
        Thread consumer2 = new Thread(consumer, "consumer2");
        consumer2.start();
        Thread consumer3 = new Thread(consumer, "consumer3");
        consumer3.start();

        Thread produсer1 = new Thread(produser, "1");
        produсer1.start();
        Thread produсer2 = new Thread(produser, "2");
        produсer2.start();
        Thread produсer3 = new Thread(produser, "3");
        produсer3.start();

        consumer1.join();
        consumer2.join();
        consumer3.join();
        produсer1.join();
        produсer2.join();
        produсer3.join();
        assertThat(buffer).containsExactlyInAnyOrder(1, 2, 3);
    }

    @Test
    void when1ConsumerAnd1Producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> block = new SimpleBlockingQueue<>(1);
        List<Integer> buffer = new CopyOnWriteArrayList<Integer>();

        Runnable produser = new Runnable() {
            @Override
            public void run() {
                try {
                    block.offer(Integer.valueOf(Thread.currentThread().getName()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable consumer = new Runnable() {
            @Override
            public void run() {
                try {
                    buffer.add(block.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread consumer1 = new Thread(consumer, "consumer1");
        consumer1.start();
        Thread produсer1 = new Thread(produser, "1");
        produсer1.start();

        consumer1.join();
        produсer1.join();
        assertThat(buffer).containsExactly(1);
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            IntStream.range(0, 5).forEach(value -> {
                try {
                    queue.offer(value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactlyInAnyOrder(0, 1, 2, 3, 4);
    }

}
