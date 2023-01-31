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
        return accounts.putIfAbsent(account.id(), account) != null;        
    }

    public synchronized boolean update(Account account) {
        return accounts.computeIfPresent(
                account.id(), (key, value) -> account) != null;        
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;         
    }

    public synchronized Optional<Account> getById(int id) {        
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;        
        Account from = getById(fromId)
                .orElseThrow(() -> new IllegalStateException(
                        "Not found account by id = " + Integer.toString(fromId)));
        Account to = getById(toId)
                .orElseThrow(() -> new IllegalStateException(
                        "Not found account by id = " + Integer.toString(toId)));
        
        if (amount > from.amount()) {
            throw new IllegalArgumentException(
                    "Transfer amount should be less then " + Integer.toString(from.amount()));
        }         
        update(new Account(fromId, from.amount() - amount));
        update(new Account(toId, to.amount() + amount));
        rsl = true;
        return rsl;
    }

}
