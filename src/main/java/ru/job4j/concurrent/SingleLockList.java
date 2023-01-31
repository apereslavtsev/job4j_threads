package ru.job4j.concurrent;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    
    @GuardedBy("this")
    private final List<T> list;
    
    @GuardedBy("this")
    private volatile int itCheck;

    public SingleLockList(List<T> list) {
        itCheck = 0;
        this.list = copy(list);        
    }

    public synchronized void add(T value) {
        itCheck++;
        list.add(value);        
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        /*Fail-safe iterator example:
         *return copy(list).iterator();*/
        
        /*Fail-fast iterator example:*/
        return new Iterator<T>() {
            private final int currentCheck = itCheck;
            private final Iterator<T> it = list.iterator();
             
            @Override
            public boolean hasNext() {
                if (currentCheck != itCheck) {
                    throw new ConcurrentModificationException();
                }
                return it.hasNext();
            }

            @Override
            public T next() {
                if (currentCheck != itCheck) {
                    throw new ConcurrentModificationException();
                }
                return it.next();
            }           
        };    
    }

    private synchronized List<T> copy(List<T> origin) {
        return new ArrayList<T>(origin);
    }

}
