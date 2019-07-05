package com.destroystokyo.paper.event.player

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack

/**
 * Called when a player is firing a bow and the server is choosing an arrow to use.
 */
open class PlayerReadyArrowEvent(player: Player,
                            /**
                             * @return the player is using to fire the arrow
                             */
                            val bow: ItemStack,
                            /**
                             * @return the arrow that is attempting to be used
                             */
                            val arrow: ItemStack) : PlayerEvent(player), Cancellable {

    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    /**
     * Whether or not use of this arrow is cancelled. On cancel, the server will try the next arrow available and fire another event.
     */
    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Cancel use of this arrow. On cancel, the server will try the next arrow available and fire another event.
     *
     * @param cancel true if you wish to cancel this event
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
