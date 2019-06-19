package org.bukkit.craftbukkit.util;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract class LazyHashSet<E> implements Set<E> {
    Set<E> reference = null;

    public int size() {
        return getReference().size();
    }

    public boolean isEmpty() {
        return getReference().isEmpty();
    }

    public boolean contains(Object o) {
        return getReference().contains(o);
    }

    @Nonnull
    public Iterator<E> iterator() {
        return getReference().iterator();
    }

    @Nonnull
    public Object[] toArray() {
        return getReference().toArray();
    }

    @Nonnull
    public <T> T[] toArray(@Nonnull T[] a) {
        return getReference().toArray(a);
    }

    public boolean add(E o) {
        return getReference().add(o);
    }

    public boolean remove(Object o) {
        return getReference().remove(o);
    }

    public boolean containsAll(@Nonnull Collection<?> c) {
        return getReference().containsAll(c);
    }

    public boolean addAll(@Nonnull Collection<? extends E> c) {
        return getReference().addAll(c);
    }

    public boolean retainAll(@Nonnull Collection<?> c) {
        return getReference().retainAll(c);
    }

    public boolean removeAll(@Nonnull Collection<?> c) {
        return getReference().removeAll(c);
    }

    public void clear() {
        getReference().clear();
    }

    public Set<E> getReference() {
        Set<E> reference = this.reference;
        if (reference != null) {
            return reference;
        }
        return this.reference = makeReference();
    }

    abstract Set<E> makeReference();

    public boolean isLazy() {
        return reference == null;
    }

    @Override
    public int hashCode() {
        return 157 * getReference().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        LazyHashSet<?> that = (LazyHashSet<?>) obj;
        return (this.isLazy() && that.isLazy()) || this.getReference().equals(that.getReference());
    }

    @Override
    public String toString() {
        return getReference().toString();
    }
}
