package ru.job4j.concurrent.threadPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = sumsI(matrix, i);
        }
        return sums;
    }
    private static Sums sumsI(int[][] matrix, int i) {
        Sums rsl = new Sums();
        rsl.setRowSum(Arrays.stream(matrix[i]).sum());
        for (int[] ints : matrix) {
            rsl.setColSum(rsl.getColSum() + ints[i]);
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();

        for (int i = 0; i < matrix.length; i++) {
            int finalI = i;
            futures.put(i,
                CompletableFuture.supplyAsync(() -> sumsI(matrix, finalI)));
        }

        Sums[] sums = new Sums[matrix.length];
        for (Integer key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

}
