package org.lavapowered.lava.internal;

import java.util.logging.Logger;

public class Lava {

    public static Logger LOGGER = Logger.getLogger("LavaInternal");
    private static final String NATIVE_VERSON = "v1_12_R1";
    private static final String NMS_PREFIX = "net/minecraft/server/";

    public static String getNativeVersion() {
        return NATIVE_VERSON;
    }

    public static String getNmsPrefix() {
        return NMS_PREFIX;
    }

}
