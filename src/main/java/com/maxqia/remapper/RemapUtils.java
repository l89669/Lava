package com.maxqia.remapper;

import java.lang.reflect.Method;

public class RemapUtils {
    private static final Method GET_CALLER_CLASS;

    static {
        try {
            final Class<?> reflection = Class.forName("sun.reflect.Reflection");
            Method getCallerClass = reflection.getMethod("getCallerClass", int.class);
            GET_CALLER_CLASS = getCallerClass;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("getCallerClass doesn't exist!");
        }
    }

    public static Class<?> getCallerClass(int skip) {
        if (GET_CALLER_CLASS != null) {
            try {
                return (Class<?>) GET_CALLER_CLASS.invoke(null, skip + 1);
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(e.getMessage());
            }
        }
        throw new RuntimeException("getCallerClass doesn't exist!");
    }

    public static ClassLoader getCallerClassloader() {
        return getCallerClass(3).getClassLoader();
    }
}
