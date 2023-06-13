package data;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArgumentsData {
    private Queue<Map<String, Integer>> arguments = new LinkedList<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void update(Map<String, Integer> args) {
        writeLock.lock();
        try {
            arguments.offer(args);
        } finally {
            writeLock.unlock();
        }
    }

    public Map<String, Integer> get() {
        readLock.lock();
        try {
            return arguments.poll();
        } finally {
            readLock.unlock();
        }
    }
}
