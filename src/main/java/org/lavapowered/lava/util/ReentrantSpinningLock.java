package org.lavapowered.lava.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ReentrantSpinningLock {
    /*
     * Impl Note:
     * A write lock can reentrant as a read lock, while a
     * read lock is not allowed to reentrant as a write lock.
     * READ LOCK IS UNTESTED, USE WITH CATION.
     */
    private final AtomicBoolean writeLocked = new AtomicBoolean(false);
    private final AtomicInteger readerThreads = new AtomicInteger(0);
    // --------- Thread local restricted fields ---------
    private long heldThreadId = 0;
    private int reentrantLocks = 0;
    // --------- Wrappers to allow typical usages ---------
    private SpinningWriteLock wrappedWriteLock = new SpinningWriteLock();
    private SpinningReadLock wrappedReadLock = new SpinningReadLock();

    /**
     * Lock as a typical reentrant write lock
     */
    public void lock() {
        long currentThreadId = Thread.currentThread().getId();
        if (heldThreadId == currentThreadId) {
            reentrantLocks++;
        } else {
            while (!writeLocked.compareAndSet(false, true)) ; // In case acquire one lock concurrently
            heldThreadId = currentThreadId;
        }
    }

    public void unlock() {
        if (reentrantLocks == 0) {
            heldThreadId = 0;
            writeLocked.set(false);
        } else {
            --reentrantLocks;
        }
    }

    /**
     * Lock as a typical reentrant read lock
     */
    @Deprecated
    public void lockWeak() {
        long currentThreadId = Thread.currentThread().getId();
        if (heldThreadId == currentThreadId) {
            reentrantLocks++;
        } else {
            if (readerThreads.get() == 0) {
                while (!writeLocked.compareAndSet(false, true)) ; // Block future write lock
            }
            heldThreadId = currentThreadId;
            readerThreads.getAndIncrement(); // Micro-optimization: this saves one plus
        }
    }

    @Deprecated
    public void unlockWeak() {
        if (reentrantLocks == 0) {
            heldThreadId = 0;
            writeLocked.set(false);
        } else {
            --reentrantLocks;
        }
    }

    public SpinningWriteLock writeLock() {
        return wrappedWriteLock;
    }

    public SpinningReadLock readLock() {
        return wrappedReadLock;
    }

    public class SpinningWriteLock {
        public void lock() {
            lock();
        }

        public void unlock() {
            unlock();
        }
    }

    @Deprecated
    public class SpinningReadLock {
        public void lock() {
            lockWeak();
        }

        public void unlock() {
            unlockWeak();
        }
    }
}