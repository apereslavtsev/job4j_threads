package ru.job4j.concurrent.cash;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {  

    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        memory.computeIfPresent(model.getId(), (id, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base tmp = new Base(id, model.getVersion() + 1);
            tmp.setName(model.getName());
            return tmp;
        });
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
