package com.destroystokyo.paper.network

import java.net.InetSocketAddress

/**
 * Represents a client connected to the server.
 */
interface NetworkClient {

    /**
     * Returns the socket address of the client.
     *
     * @return The client's socket address
     */
    val address: InetSocketAddress

    /**
     * Returns the protocol version of the client.
     *
     * @return The client's protocol version, or `-1` if unknown
     * @see [List of protocol
     * version numbers](http://wiki.vg/Protocol_version_numbers)
     */
    val protocolVersion: Int

    /**
     * Returns the virtual host the client is connected to.
     *
     *
     * The virtual host refers to the hostname/port the client used to
     * connect to the server.
     *
     * @return The client's virtual host, or `null` if unknown
     */
    val virtualHost: InetSocketAddress?
}
