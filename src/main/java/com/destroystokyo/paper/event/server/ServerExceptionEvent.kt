package com.destroystokyo.paper.event.server

import com.destroystokyo.paper.exception.ServerException
import com.google.common.base.Preconditions
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Called whenever an exception is thrown in a recoverable section of the server.
 */
open class ServerExceptionEvent(exception: ServerException) : Event() {
    /**
     * Gets the wrapped exception that was thrown.
     *
     * @return Exception thrown
     */
    val exception: ServerException = Preconditions.checkNotNull(exception, "exception")

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
