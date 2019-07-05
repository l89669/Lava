package org.bukkit.event.command

import org.bukkit.command.CommandSender
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Thrown when a player executes a command that is not defined
 */
open class UnknownCommandEvent(
        /**
         * Gets the CommandSender or ConsoleCommandSender
         *
         *
         *
         * @return Sender of the command
         */
        val sender: CommandSender,
        /**
         * Gets the command that was send
         *
         *
         *
         * @return Command sent
         */
        val commandLine: String,
        /**
         * Gets message that will be returned
         *
         *
         *
         * @return Unknown command message
         */
        /**
         * Sets message that will be returned
         *
         *
         * Set to null to avoid any message being sent
         *
         * @param message the message to be returned, or null
         */
        var message: String?) : Event(false) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
