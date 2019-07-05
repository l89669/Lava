package com.destroystokyo.paper.event.player

import org.apache.commons.lang3.Validate
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

/**
 * This event is fired during a player handshake.
 *
 *
 * If there are no listeners listening to this event, the logic default
 * to your server platform will be ran.
 *
 *
 * WARNING: TAMPERING WITH THIS EVENT CAN BE DANGEROUS
 */
open class PlayerHandshakeEvent
/**
 * Creates a new [PlayerHandshakeEvent].
 *
 * @param originalHandshake the original handshake string
 * @param cancelled if this event is enabled
 */
(
        /**
         * Gets the original handshake string.
         *
         * @return the original handshake string
         */
        val originalHandshake: String, private var cancelled: Boolean) : Event(), Cancellable {
    /**
     * Gets the server hostname string.
     *
     *
     * This should not include the port.
     *
     * @return the server hostname string
     */
    /**
     * Sets the server hostname string.
     *
     *
     * This should not include the port.
     *
     * @param serverHostname the server hostname string
     */
    var serverHostname: String? = null
    /**
     * Gets the socket address hostname string.
     *
     *
     * This should not include the port.
     *
     * @return the socket address hostname string
     */
    /**
     * Sets the socket address hostname string.
     *
     *
     * This should not include the port.
     *
     * @param socketAddressHostname the socket address hostname string
     */
    var socketAddressHostname: String? = null
    /**
     * Gets the unique id.
     *
     * @return the unique id
     */
    /**
     * Sets the unique id.
     *
     * @param uniqueId the unique id
     */
    var uniqueId: UUID? = null
    /**
     * Gets the profile properties.
     *
     *
     * This should be a valid JSON string.
     *
     * @return the profile properties, as JSON
     */
    /**
     * Sets the profile properties.
     *
     *
     * This should be a valid JSON string.
     *
     * @param propertiesJson the profile properties, as JSON
     */
    var propertiesJson: String? = null
    /**
     * Determines if authentication failed.
     *
     *
     * When `true`, the client connecting will be disconnected
     * with the [fail message][.getFailMessage].
     *
     * @return `true` if authentication failed, `false` otherwise
     */
    /**
     * Sets if authentication failed and the client should be disconnected.
     *
     *
     * When `true`, the client connecting will be disconnected
     * with the [fail message][.getFailMessage].
     *
     * @param failed `true` if authentication failed, `false` otherwise
     */
    var isFailed: Boolean = false
    /**
     * Gets the message to display to the client when authentication fails.
     *
     * @return the message to display to the client
     */
    /**
     * Sets the message to display to the client when authentication fails.
     *
     * @param failMessage the message to display to the client
     */
    var failMessage = "If you wish to use IP forwarding, please enable it in your BungeeCord config as well!"
        set(failMessage) {
            Validate.notEmpty(failMessage, "fail message cannot be null or empty")
            field = failMessage
        }

    /**
     * Determines if this event is cancelled.
     *
     *
     * When this event is cancelled, custom handshake logic will not
     * be processed.
     *
     * @return `true` if this event is cancelled, `false` otherwise
     */
    override fun isCancelled(): Boolean {
        return this.cancelled
    }

    /**
     * Sets if this event is cancelled.
     *
     *
     * When this event is cancelled, custom handshake logic will not
     * be processed.
     *
     * @param cancelled `true` if this event is cancelled, `false` otherwise
     */
    override fun setCancelled(cancelled: Boolean) {
        this.cancelled = cancelled
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
