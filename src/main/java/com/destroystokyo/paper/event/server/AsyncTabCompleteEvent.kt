package com.destroystokyo.paper.event.server

import org.apache.commons.lang3.Validate
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

/**
 * Allows plugins to compute tab completion results asynchronously. If this event provides completions, then the standard synchronous process will not be fired to populate the results. However, the synchronous TabCompleteEvent will fire with the Async results.
 *
 * Only 1 process will be allowed to provide completions, the Async Event, or the standard process.
 */
open class AsyncTabCompleteEvent(
        /**
         * Get the sender completing this command.
         *
         * @return the [CommandSender] instance
         */
        val sender: CommandSender, private var completions: List<String>?,
        /**
         * Return the entire buffer which formed the basis of this completion.
         *
         * @return command buffer, as entered
         */
        val buffer: String,
        /**
         * @return True if it is a command being tab completed, false if it is a chat message.
         */
        val isCommand: Boolean,
        /**
         * @return The position looked at by the sender, or null if none
         */
        val location: Location) : Event(true), Cancellable {
    private var cancelled: Boolean = false
    /**
     * If true, the standard process of calling [Command.tabComplete]
     * or current player names will not be called.
     *
     * @return Is completions considered handled. Always true if completions is not empty.
     */
    /**
     * Sets whether or not to consider the completion request handled.
     * If true, the standard process of calling [Command.tabComplete]
     * or current player names will not be called.
     *
     * @param handled if this completion should be marked as being handled
     */
    var isHandled = false
        get() = completions!!.isNotEmpty() || field
    private val fireSyncHandler = true

    /**
     * The list of completions which will be offered to the sender, in order.
     * This list is mutable and reflects what will be offered.
     *
     * If this collection is not empty after the event is fired, then
     * the standard process of calling [Command.tabComplete]
     * or current player names will not be called.
     *
     * @return a list of offered completions
     */
    fun getCompletions(): List<String>? {
        return completions
    }

    /**
     * Set the completions offered, overriding any already set.
     * If this collection is not empty after the event is fired, then
     * the standard process of calling [Command.tabComplete]
     * or current player names will not be called.
     *
     * The passed collection will be cloned to a new List. You must call {[.getCompletions]} to mutate from here
     *
     * @param completions the new completions
     */
    fun setCompletions(completions: List<String>) {
        Validate.notNull(completions)
        this.completions = ArrayList(completions)
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Will provide no completions, and will not fire the synchronous process
     * @param cancelled true if you wish to cancel this event
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
