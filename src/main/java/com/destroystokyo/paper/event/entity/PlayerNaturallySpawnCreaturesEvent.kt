package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Fired when the server is calculating what chunks to try to spawn monsters in every Monster Spawn Tick event
 */
open class PlayerNaturallySpawnCreaturesEvent(player: Player,
        /**
         * @return The radius of chunks around this player to be included in natural spawn selection
         */
                                         /**
                                          * @param radius The radius of chunks around this player to be included in natural spawn selection
                                          */
                                         var spawnRadius: Byte) : PlayerEvent(player), Cancellable {

    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    /**
     * @return If this players chunks will be excluded from natural spawns
     */
    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * @param cancel true if you wish to cancel this event, and not include this players chunks for natural spawning
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
