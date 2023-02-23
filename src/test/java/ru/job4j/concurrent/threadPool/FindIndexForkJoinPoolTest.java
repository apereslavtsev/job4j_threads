package ru.job4j.concurrent.threadPool;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ForkJoinPool;

import org.junit.jupiter.api.Test;

class FindIndexForkJoinPoolTest {

    @Test
    void whenSizeIs9AndTypeIsUserThenLinearSearchOk() {
        int size = 9;
        User[] array = new User[size];
        for (int i = 0; i < size; i++) {
            array[i] = new User("name" + Integer.toString(i), 
                    "email" + Integer.toString(i));
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        User userToSearch = new User("name3", "email3"); 
        
        assertThat(forkJoinPool.invoke(
                new FindIndexForkJoinPool(array, 0, size, userToSearch)))
        .isEqualTo(3);
    }
    
    @Test
    void whenSizeIs10000AndTypeIsUserThenParallelSearchOk() {
        int size = 10000;
        User[] array = new User[size];
        for (int i = 0; i < size; i++) {
            array[i] = new User("name" + Integer.toString(i), 
                    "email" + Integer.toString(i));
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        User userToSearch = new User("name7700", "email7700"); 
        
        assertThat(forkJoinPool.invoke(
                new FindIndexForkJoinPool(array, 0, size, userToSearch)))
        .isEqualTo(7700);
    }
    
    @Test
    void whenSizetIs10000AndTypeIsStringThenParallelSearchOk() {
        int size = 10000;
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = "String" + Integer.toString(i);
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        
        assertThat(forkJoinPool.invoke(
                new FindIndexForkJoinPool(array, 0, size, "String9999")))
        .isEqualTo(9999);
    }
    
    
    @Test
    void whenSizeIs10000AndTypeIsStringThenParallelSearchNotFound() {
        int size = 10000;
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = "String" + Integer.toString(i);
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        
        assertThat(forkJoinPool.invoke(
                new FindIndexForkJoinPool(array, 0, size, "String")))
        .isEqualTo(-1);
    }
    

    @Test
    void whenSizeIs9AndTypeIsUserThenLinearSearchNotFound() {
        int size = 9;
        User[] array = new User[size];
        for (int i = 0; i < size; i++) {
            array[i] = new User("name" + Integer.toString(i), 
                    "email" + Integer.toString(i));
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        User userToSearch = new User("nameNotFound", "emailNotFound"); 
        
        assertThat(forkJoinPool.invoke(
                new FindIndexForkJoinPool(array, 0, size, userToSearch)))
        .isEqualTo(-1);
    }

}
