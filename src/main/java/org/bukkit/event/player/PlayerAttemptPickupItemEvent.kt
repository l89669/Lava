package org.bukkit.event.player

import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList

/**
 * Thrown when a player attempts to pick an item up from the ground
 */
open class PlayerAttemptPickupItemEvent(player: Player,
                                        /**
                                         * Gets the Item attempted by the player.
                                         *
                                         * @return Item
                                         */
                                        val item: Item,
                                        /**
                                         * Gets the amount that will remain on the ground, if any
                                         *
                                         * @return amount that will remain on the ground
                                         */
                                        val remaining: Int) : PlayerEvent(player), Cancellable {
    /**
     * Gets if the item will fly at the player
     *
     * @return True if the item will fly at the player
     */
    /**
     * Set if the item will fly at the player
     *
     * Cancelling the event will set this value to false.
     *
     * @param flyAtPlayer True for item to fly at player
     */
    var flyAtPlayer = true
    private var isCancelled = false

    @Deprecated("") // Remove in 1.13
    constructor(player: Player, item: Item) : this(player, item, 0)

    override fun isCancelled(): Boolean {
        return this.isCancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.isCancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
