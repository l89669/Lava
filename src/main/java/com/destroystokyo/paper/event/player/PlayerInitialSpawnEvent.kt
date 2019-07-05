package com.destroystokyo.paper.event.player

import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

open class PlayerInitialSpawnEvent(player: Player,
        /**
         * Gets the current spawn location
         *
         * @return Location current spawn location
         */
                              /**
                               * Sets the new spawn location
                               *
                               * @param spawnLocation new location for the spawn
                               */
                              var spawnLocation: Location?) : PlayerEvent(player) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
