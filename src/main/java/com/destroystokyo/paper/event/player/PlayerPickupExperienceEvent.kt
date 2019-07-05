package com.destroystokyo.paper.event.player

import org.bukkit.entity.ExperienceOrb
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Fired when a player is attempting to pick up an experience orb
 */
open class PlayerPickupExperienceEvent(player: Player,
                                  /**
                                   * @return Returns the Orb that the player is picking up
                                   */
                                  val experienceOrb: ExperienceOrb) : PlayerEvent(player), Cancellable {

    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * If true, Cancels picking up the experience orb, leaving it in the world
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
