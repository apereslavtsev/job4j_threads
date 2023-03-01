package ru.job4j.concurrent.threadPool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

class RolColSumTest {

    @Test
    void whenLinearCalcSum() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        Sums[] rsl = new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        assertThat(RolColSum.sum(matrix))
                .isEqualTo(rsl);
    }
    @Test
    void whenAsyncCalcSum() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        Sums[] rsl = new Sums[] {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };

        assertThat(RolColSum.asyncSum(matrix))
                .isEqualTo(rsl);
    }
}