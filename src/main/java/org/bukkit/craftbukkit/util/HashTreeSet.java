package org.bukkit.craftbukkit.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class HashTreeSet<V> implements Set<V> {

    private HashSet<V> hash = new HashSet<>();
    private TreeSet<V> tree = new TreeSet<>();

    public HashTreeSet() {

    }

    @Override
    public int size() {
        return hash.size();
    }

    @Override
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return hash.contains(o);
    }

    @Override
    @Nonnull
    public Iterator<V> iterator() {
        return new Iterator<V>() {

            private Iterator<V> it = tree.iterator();
            private V last;

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public V next() {
                return last = it.next();
            }

            @Override
            public void remove() {
                if (last == null) {
                    throw new IllegalStateException();
                }
                it.remove();
                hash.remove(last);
                last = null;
            }
        };
    }

    @Override
    @Nonnull
    public Object[] toArray() {
        return hash.toArray();
    }

    @Override
    @Nonnull
    public Object[] toArray(@Nonnull Object[] a) {
        return hash.toArray(a);
    }

    @Override
    public boolean add(V e) {
        hash.add(e);
        return tree.add(e);
    }

    @Override
    public boolean remove(Object o) {
        hash.remove(o);
        return tree.remove(o);
    }

    @Override
    public boolean containsAll(@Nonnull Collection c) {
        return hash.containsAll(c);
    }

    @Override
    public boolean addAll(@Nonnull Collection c) {
        tree.addAll(c);
        return hash.addAll(c);
    }

    @Override
    public boolean retainAll(@Nonnull Collection c) {
        tree.retainAll(c);
        return hash.retainAll(c);
    }

    @Override
    public boolean removeAll(@Nonnull Collection c) {
        tree.removeAll(c);
        return hash.removeAll(c);
    }

    @Override
    public void clear() {
        hash.clear();
        tree.clear();
    }

    public V first() {
        return tree.first();
    }

}
