package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Optional;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class AccountStorage {
    
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return false;
    }

    public boolean update(Account account) {
        return false;
    }

    public boolean delete(int id) {
        return false;
    }

    public Optional<Account> getById(int id) {
        return Optional.empty();
    }

    public boolean transfer(int fromId, int toId, int amount) {
        return false;
    }

}
