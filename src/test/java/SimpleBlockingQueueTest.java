import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import ru.job4j.concurrent.SimpleBlockingQueue;

class SimpleBlockingQueueTest {     
    
    @Test
    void when3ConsumerAnd3Producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> block = new SimpleBlockingQueue<>(1); 
        List<String> rsl = new ArrayList<>();
        
        Runnable produser = new Runnable() {            
            @Override
            public void run() {
                rsl.add("before produce " + Thread.currentThread().getName());
                try {
                    block.offer(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } 
                rsl.add("after produce " + Thread.currentThread().getName());                
            }
        };        
        
        Runnable consumer = new Runnable() {            
            @Override
            public void run() {
                rsl.add("before consume " + Thread.currentThread().getName());
                try {
                    block.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } 
                rsl.add("after consume " + Thread.currentThread().getName());                
            }
        }; 
        
        Thread consumer1 = new Thread(consumer, "comsumer1");        
        consumer1.start();
        Thread consumer2 = new Thread(consumer, "consumer2");        
        consumer2.start(); 
        Thread consumer3 = new Thread(consumer, "consumer3");        
        consumer3.start(); 
        Thread produсer1 = new Thread(produser, "produсer1");
        produсer1.start();       
        Thread produсer2 = new Thread(produser, "produсer2");
        produсer2.start();        
        Thread produсer3 = new Thread(produser, "produсer3");
        produсer3.start(); 
        
        consumer1.join();
        consumer2.join();
        consumer3.join();
        produсer1.join();
        produсer2.join();
        produсer3.join();        
        
        assertThat(rsl.size()).isEqualTo(12);
    }
    
    @Test
    void when1ConsumerAnd1Producer() throws InterruptedException {
        SimpleBlockingQueue<Integer> block = new SimpleBlockingQueue<>(1); 
        List<String> rsl = new ArrayList<>();
        
        Runnable produser = new Runnable() {            
            @Override
            public void run() {
                rsl.add("before produce " + Thread.currentThread().getName());
                try {
                    block.offer(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } 
                rsl.add("after produce " + Thread.currentThread().getName());                
            }
        };        
        
        Runnable consumer = new Runnable() {            
            @Override
            public void run() {
                rsl.add("before consume " + Thread.currentThread().getName());
                try {
                    block.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } 
                rsl.add("after consume " + Thread.currentThread().getName());                
            }
        }; 
        
        Thread consumer1 = new Thread(consumer, "comsumer101");
        consumer1.start();
        Thread producer1 = new Thread(produser, "produser101");
        producer1.start();
        
        consumer1.join();
        producer1.join();
        
        assertThat(rsl.size()).isEqualTo(4);
    }
}


