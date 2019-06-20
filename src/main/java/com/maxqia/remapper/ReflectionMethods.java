package com.maxqia.remapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionMethods {

    private final static ConcurrentHashMap<String, String> fieldGetNameCache = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String, String> methodGetNameCache = new ConcurrentHashMap<>();
    private final static ConcurrentHashMap<String, String> simpleNameGetNameCache = new ConcurrentHashMap<>();

    public static Class<?> forName(String className) throws ClassNotFoundException {
        return forName(className, true, RemapUtils.getCallerClassloader());
    }

    public static Class<?> forName(String className, boolean initialize, ClassLoader classLoader) throws ClassNotFoundException {
        if (!className.startsWith("net.minecraft.server." + RemapUtils.NMS_VERSION)) {
            return Class.forName(className, initialize, classLoader);
        }
        className = ReflectionTransformer.jarMapping.classes.getOrDefault(className.replace('.', '/'), className).replace('/', '.');
        return Class.forName(className, initialize, classLoader);
    }

    // Get Fields
    public static Field getField(final Class<?> inst, String name) throws NoSuchFieldException, SecurityException {
        if (RemapUtils.isClassNeedRemap(inst, true)) {
            name = RemapUtils.mapFieldName(inst, name);
        }
        return inst.getField(name);
    }

    public static Field getDeclaredField(final Class<?> inst, String name) throws NoSuchFieldException, SecurityException {
        if (RemapUtils.isClassNeedRemap(inst, false)) {
            name = ReflectionTransformer.remapper.mapFieldName(RemapUtils.reverseMap(inst), name, (String) null);
        }
        return inst.getDeclaredField(name);
    }

    // Get Methods
    public static Method getMethod(final Class<?> inst, String name, final Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        if (RemapUtils.isClassNeedRemap(inst, true)) {
            name = RemapUtils.mapMethod(inst, name, parameterTypes);
        }
        try {
            return inst.getMethod(name, parameterTypes);
        } catch (NoClassDefFoundError e) {
            throw new NoSuchMethodException(e.toString());
        }
    }

    public static Method getDeclaredMethod(final Class<?> inst, String name, final Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException {
        if (RemapUtils.isClassNeedRemap(inst, true)) {
            name = RemapUtils.mapMethod(inst, name, parameterTypes);
        }
        try {
            return inst.getDeclaredMethod(name, parameterTypes);
        } catch (NoClassDefFoundError e) {
            throw new NoSuchMethodException(e.toString());
        }
    }

    // getName
    public static String getName(final Field field) {
        if (!RemapUtils.isClassNeedRemap(field.getDeclaringClass(), false)) {
            return field.getName();
        }
        final String hash = String.valueOf(field.hashCode());
        final String cache = ReflectionMethods.fieldGetNameCache.get(hash);
        if (cache != null) {
            return cache;
        }
        final String retn = RemapUtils.demapFieldName(field);
        ReflectionMethods.fieldGetNameCache.put(hash, retn);
        return retn;
    }

    public static String getName(final Method method) {
        if (!RemapUtils.isClassNeedRemap(method.getDeclaringClass(), true)) {
            return method.getName();
        }
        final String hash = String.valueOf(method.hashCode());
        final String cache = ReflectionMethods.methodGetNameCache.get(hash);
        if (cache != null) {
            return cache;
        }
        final String retn = RemapUtils.demapMethodName(method);
        ReflectionMethods.methodGetNameCache.put(hash, retn);
        return retn;
    }

    // getSimpleName
    public static String getSimpleName(final Class<?> inst) {
        if (!RemapUtils.isClassNeedRemap(inst, false)) {
            return inst.getSimpleName();
        }
        final String hash = String.valueOf(inst.hashCode());
        final String cache = ReflectionMethods.simpleNameGetNameCache.get(hash);
        if (cache != null) {
            return cache;
        }
        final String[] name = RemapUtils.reverseMapExternal(inst).split("\\.");
        final String retn = name[name.length - 1];
        ReflectionMethods.simpleNameGetNameCache.put(hash, retn);
        return retn;
    }

    // ClassLoader.loadClass
    public static Class<?> loadClass(final ClassLoader inst, String className) throws ClassNotFoundException {
        if (className.startsWith("net.minecraft.")) {
            className = RemapUtils.mapClass(className.replace('.', '/')).replace('/', '.');
        }
        return inst.loadClass(className);
    }
}