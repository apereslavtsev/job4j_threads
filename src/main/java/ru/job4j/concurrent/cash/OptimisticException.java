package ru.job4j.concurrent.cash;

public class OptimisticException extends RuntimeException {
    
    public OptimisticException(String message) {
        super(message);
    }

}
