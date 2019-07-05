package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Enderman
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired when an Enderman determines if it should attack a player or not.
 * Starts off cancelled if the player is wearing a pumpkin head or is not looking
 * at the Enderman, according to Vanilla rules.
 */
open class EndermanAttackPlayerEvent(entity: Enderman,
                                /**
                                 * The player the Enderman is considering attacking
                                 *
                                 * @return The player the Enderman is considering attacking
                                 */
                                val player: Player) : EntityEvent(entity), Cancellable {

    private var cancelled = false

    /**
     * The enderman considering attacking
     *
     * @return The enderman considering attacking
     */
    override fun getEntity(): Enderman {
        return super.getEntity() as Enderman
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    /**
     * @return If cancelled, the enderman will not attack
     */
    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Cancels if the Enderman will attack this player
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
