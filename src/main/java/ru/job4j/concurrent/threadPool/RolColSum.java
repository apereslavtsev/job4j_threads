package ru.job4j.concurrent.threadPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Sums sums)) {
                return false;
            }
            return getRowSum() == sums.getRowSum()
                    && getColSum() == sums.getColSum();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRowSum(), getColSum());
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumsI(matrix, i);
        }
        return sums;
    }
    private static Sums sumsI(int[][] matrix, int i) {
        Sums rsl = new Sums();
        rsl.rowSum = Arrays.stream(matrix[i]).sum();
        for (int[] ints : matrix) {
            rsl.colSum += ints[i];
        }
        return rsl;
    }

    public static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> sumsI(matrix, i));
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();

        for (int i = 0; i < matrix.length; i++) {
            futures.put(i, getTask(matrix, i));
        }

        Sums[] sums = new Sums[matrix.length];
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }
}
