package org.lavapowered.lava.net;

import javax.annotation.concurrent.Immutable;
import java.net.InetAddress;

@Immutable
public class LocalAddress {
    private final InetAddress host;
    private final int port;

    public static LocalAddress create(InetAddress localHost, int localPort) {
        return new LocalAddress(localHost, localPort);
    }

    public LocalAddress(InetAddress localHost, int localPort) {
        host = localHost;
        port = localPort;
    }

    public InetAddress host() {
        return host;
    }

    public int port() {
        return port;
    }
}