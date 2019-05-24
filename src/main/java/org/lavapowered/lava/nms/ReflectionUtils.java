package org.lavapowered.lava.nms;

public class ReflectionUtils {

    private static SecurityManager securityManager = new SecurityManager();

    public static Class<?> getCallerClass(int s) {
        return securityManager.getCallerClass(s);
    }

    public static ClassLoader getCallerClassLoader() {
        return ReflectionUtils.getCallerClass(3).getClassLoader();
    }

    static class SecurityManager extends java.lang.SecurityManager {

        public Class<?> getCallerClass(int skip) {
            return getClassContext()[skip + 1];
        }
    }

}
