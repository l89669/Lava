package com.maxqia.remapper;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class Utils {
    public static String reverseMapExternal(Class<?> name) {
        return reverseMap(name).replace('$', '.').replace('/', '.');
    }

    public static String reverseMap(Class<?> name) {
        return reverseMap(Type.getInternalName(name));
    }

    public static String reverseMap(String check) {
        for (Map.Entry<String, String> entry : Transformer.jarMapping.classes.entrySet()) {
            if (entry.getValue().equals(check)) {
                return entry.getKey();
            }
        }
        return check;
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

        for (Map.Entry<String, String> entry : Transformer.jarMapping.methods.entrySet()) {
            if (entry.getKey().startsWith(match)) {

                // Check type to see if it matches
                String[] str = entry.getKey().split("\\s+");
                int i = 0;
                boolean failed = false;
                for (Type type : Type.getArgumentTypes(str[1])) {
                    if (!type.getClassName().equals(reverseMapExternal(parameterTypes[i]))) {
                        failed = true;
                        break;
                    }
                }

                if (!failed) {
                    return entry.getValue();
                }
            }
        }

        // Search interfaces
        ArrayList<Class<?>> parents = new ArrayList<>();
        parents.add(inst.getSuperclass());
        parents.addAll(Arrays.asList(inst.getInterfaces()));

        for (Class<?> superClass : parents) {
            if (superClass == null) {
                continue;
            }
            mapMethodInternal(superClass, name, parameterTypes);
        }
        return null;
    }
}
