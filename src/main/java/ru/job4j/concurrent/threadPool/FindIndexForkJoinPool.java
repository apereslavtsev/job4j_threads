package ru.job4j.concurrent.threadPool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindIndexForkJoinPool<T> extends RecursiveTask<Integer> {
    
    private final T[] array;    
    
    private final int from;
    
    private final int to;
    
    private final T objToSearch;

    public FindIndexForkJoinPool(T[] array, int from, int to, T objToSearch) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.objToSearch = objToSearch;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findWithLineSearch();
        }
        int mid = (from + to) / 2;
        FindIndexForkJoinPool<T> firstFinderIndexInArray = new FindIndexForkJoinPool<>(
                array, from, mid, objToSearch);
        FindIndexForkJoinPool<T> secondFinderIndexInArray = new FindIndexForkJoinPool<>(
                array, mid + 1, to, objToSearch);
        
        firstFinderIndexInArray.fork();
        secondFinderIndexInArray.fork();
        
        return Math.max(firstFinderIndexInArray.join(),secondFinderIndexInArray.join());
    }

    private int findWithLineSearch() {
        for (int i = from; i < to; i++) {
            if (objToSearch.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public static <T> Integer findIndex(T[] arr, T toSearch) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        return forkJoinPool.invoke(
                new FindIndexForkJoinPool<>(arr, 0, arr.length, toSearch));
        
    }
}
