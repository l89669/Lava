package org.spigotmc;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Utilities for creating and working with {@code Supplier} instances.
 */
public class SupplierUtils {

    /**
     * Creates a new {@code Supplier} that supplies the given {@code Supplier}'s
     * first value.
     *
     * @param doLazily {@code false}, if {@code completion.get()} should be
     *                 called immediately, or {@code true}, if {@code completion.get()} should
     *                 be called only when the value is first needed.
     */
    public static <V> Supplier<V> createUnivaluedSupplier(Supplier<V> completion, boolean doLazily) {
        return doLazily ? new LazyHeadSupplier<V>(completion) : new ValueSupplier<V>(completion.get());
    }

    /**
     * Returns {@code supplier.get()}, if {@code supplier} is non-{@code null}
     * (or {@code null}, otherwise).
     */
    @Nullable
    public static <V> V getIfExists(@Nullable Supplier<V> supplier) {
        return supplier != null ? supplier.get() : null;
    }

    /**
     * Repeatedly supplies the first value from a given sequence; the value is
     * obtained only when required.
     */
    public static class LazyHeadSupplier<V> implements Supplier<V> {

        @Nullable
        private Supplier<V> completion;
        @Nullable
        private V value;

        public LazyHeadSupplier(Supplier<V> completion) {
            this.completion = completion;
        }

        @Nullable
        public synchronized V get() {
            if (this.completion != null) {
                this.value = this.completion.get();
                this.completion = null;
            }

            return this.value;
        }
    }

    /**
     * Repeatedly supplies the given value.
     */
    public static class ValueSupplier<V> implements Supplier<V> {

        @Nullable
        private final V value;

        public ValueSupplier(@Nullable V value) {
            this.value = value;
        }

        @Nullable
        public V get() {
            return this.value;
        }
    }
}
