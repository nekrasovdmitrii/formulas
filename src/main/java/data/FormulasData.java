package data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FormulasData {
    private Map<String, List<Token>> formulas = new HashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void update(Map<String, List<Token>> newFormulas) {
        writeLock.lock();
        try {
            formulas.clear();
            formulas.putAll(newFormulas);
        } finally {
            writeLock.unlock();
        }
    }

    public Map<String, List<Token>> get() {
        readLock.lock();
        try {
            return formulas;
        } finally {
            readLock.unlock();
        }
    }
}
