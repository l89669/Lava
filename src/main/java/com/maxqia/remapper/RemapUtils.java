package com.maxqia.remapper;

import net.md_5.specialsource.JarRemapper;
import org.objectweb.asm.Type;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemapUtils {

    private static ConcurrentHashMap<String, Boolean> classNeedRemap = new ConcurrentHashMap<String, Boolean>();
    public static final String NMS_PREFIX = "net/minecraft/server/";
    public static final String NMS_VERSION = "v1_12_R1";
    private static SecurityManager sm = new SecurityManager();

    public static Class<?> getCallerClass(final int skip) {
        return sm.getCallerClass(skip);
    }

    public static ClassLoader getCallerClassloader() {
        return getCallerClass(3).getClassLoader();
    }

    public static Class<?>[] getStackClass() {
        return sm.getStackClass();
    }

    public static Unsafe getUnsafe() {
        try {
            final Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe)field.get(null);
        }
        catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String reverseMapExternal(Class<?> name) {
        return reverseMap(name).replace('$', '.').replace('/', '.');
    }

    public static String reverseMap(Class<?> name) {
        return reverseMap(Type.getInternalName(name));
    }

    public static String reverseMap(String check) {
        return ReflectionTransformer.classDeMapping.getOrDefault(check, check);
    }

    public static String mapMethod(Class<?> inst, String name, Class<?>... parameterTypes) {
        String result = mapMethodInternal(inst, name, parameterTypes);
        if (result != null) {
            return result;
        }
        return name;
    }

    public static String mapMethodInternal(Class<?> inst, String name, Class<?>... parameterTypes) {
        String match = reverseMap(inst) + "/" + name;

        Collection<String> colls = ReflectionTransformer.methodFastMapping.get(match);
        for (String value : colls) {
            String[] str = value.split("\\s+");
            int i = 0;
            for (Type type : Type.getArgumentTypes(str[1])) {
                String typename = (type.getSort() == Type.ARRAY ? type.getInternalName() : type.getClassName());
                if (i >= parameterTypes.length || !typename.equals(reverseMapExternal(parameterTypes[i]))) {
                    i=-1;
                    break;
                }
                i++;
            }

            if (i >= parameterTypes.length) {
                return ReflectionTransformer.jarMapping.methods.get(value);
            }
        }

        Class superClass = inst.getSuperclass();
        if (superClass != null) {
            String superMethodName = mapMethodInternal(superClass, name, parameterTypes);
            if (superMethodName != null) {
                return superMethodName;
            }
        }
        for (final Class<?> interfaceClass : inst.getInterfaces()) {
            final String superMethodName1 = mapMethodInternal(interfaceClass, name, parameterTypes);
            if (superMethodName1 != null) {
                return superMethodName1;
            }
        }
        return null;
    }

    public static String mapFieldName(final Class<?> inst, final String name) {
        final String key = reverseMap(inst) + "/" + name;
        String mapped = ReflectionTransformer.jarMapping.fields.get(key);
        if (mapped == null) {
            final Class<?> superClass = inst.getSuperclass();
            if (superClass != null) {
                mapped = mapFieldName(superClass, name);
            }
        }
        return (mapped != null) ? mapped : name;
    }

    public static String mapClass(String className) {
        String tRemapped = JarRemapper.mapTypeName(className, ReflectionTransformer.jarMapping.packages, ReflectionTransformer.jarMapping.classes, className);
        if (tRemapped.equals(className) && className.startsWith(NMS_PREFIX) && !className.contains(NMS_VERSION)) {
            String tNewClassStr = NMS_PREFIX + NMS_VERSION + "/" + className.substring(NMS_PREFIX.length());
            return JarRemapper.mapTypeName(tNewClassStr, ReflectionTransformer.jarMapping.packages, ReflectionTransformer.jarMapping.classes, className);
        }
        return tRemapped;
    }

    public static String demapFieldName(Field field) {
        String name = field.getName();
        String match = reverseMap(field.getDeclaringClass());

        Collection<String> colls = ReflectionTransformer.fieldDeMapping.get(name);

        for (String value : colls) {
            if (value.startsWith(match)) {
                String[] matched = value.split("\\/");
                String rtr =  matched[matched.length - 1];
                return rtr;
            }
        }

        return name;
    }

    public static String demapMethodName(Method method) {
        String name = method.getName();
        String match = reverseMap(method.getDeclaringClass());

        Collection<String> colls = ReflectionTransformer.methodDeMapping.get(name);

        for (String value : colls) {
            if (value.startsWith(match)) {
                String[] matched = value.split("\\s+")[0].split("\\/");
                String rtr =  matched[matched.length - 1];
                return rtr;
            }
        }

        return name;
    }

    public static boolean isClassNeedRemap(Class<?> clazz, final boolean checkSuperClass) {
        final String className = clazz.getName();
        final Boolean cache = RemapUtils.classNeedRemap.get(className);
        if (cache != null) {
            return cache;
        }
        while (clazz != null && clazz.getClassLoader() != null) {
            if (clazz.getName().startsWith("net.minecraft.")) {
                RemapUtils.classNeedRemap.put(className, true);
                return true;
            }
            if (!checkSuperClass) {
                return false;
            }
            for (final Class<?> interfaceClass : clazz.getInterfaces()) {
                if (isClassNeedRemap(interfaceClass, true)) {
                    return true;
                }
            }
            clazz = clazz.getSuperclass();
        }
        RemapUtils.classNeedRemap.put(className, false);
        return false;
    }

    static class SecurityManager extends java.lang.SecurityManager
    {
        public Class<?> getCallerClass(final int skip) {
            return (Class<?>)this.getClassContext()[skip + 1];
        }

        public Class<?>[] getStackClass() {
            return (Class<?>[])this.getClassContext();
        }
    }
}