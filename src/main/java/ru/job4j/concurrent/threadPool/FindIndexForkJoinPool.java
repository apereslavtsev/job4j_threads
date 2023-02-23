package ru.job4j.concurrent.threadPool;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.RecursiveTask;

public class FindIndexForkJoinPool extends RecursiveTask<Integer> {
    
    private final Object[] array;    
    
    private final int from;
    
    private final int to;
    
    private final Object objToSearch;

    
    public FindIndexForkJoinPool(Object[] array, int from, int to, Object objToSearch) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.objToSearch = objToSearch;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int i = from; i < to; i++) {
                if (objToSearch.equals(array[i])) {
                    return i;
                }                
            }            
            return -1;
        }
        int mid = (from + to) / 2;
        FindIndexForkJoinPool firstFinderIndexInArray = new FindIndexForkJoinPool(
                array, from, mid, objToSearch);
        FindIndexForkJoinPool secondFinderIndexInArray = new FindIndexForkJoinPool(
                array, mid + 1, to, objToSearch);
        
        firstFinderIndexInArray.fork();
        secondFinderIndexInArray.fork();
        
        int first = firstFinderIndexInArray.join(); 
        int second = secondFinderIndexInArray.join(); 
        if (first > -1) {
            return first;
        } else if (second > -1) {
            return second;
        } else {
            return -1;
        }
    }
}
