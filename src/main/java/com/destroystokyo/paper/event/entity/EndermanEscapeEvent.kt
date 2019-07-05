package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Enderman
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

open class EndermanEscapeEvent(entity: Enderman,
                          /**
                           * @return The reason the enderman is trying to escape
                           */
                          val reason: Reason) : EntityEvent(entity), Cancellable {

    private var cancelled = false

    override fun getEntity(): Enderman {
        return super.getEntity() as Enderman
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * Cancels the escape.
     *
     * If this escape normally would of resulted in damage avoidance such as indirect,
     * the enderman will now take damage.
     *
     * @param cancel true if you wish to cancel this event
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    enum class Reason {
        /**
         * The enderman has stopped attacking and ran away
         */
        RUNAWAY,
        /**
         * The enderman has teleported away due to indirect damage (ranged)
         */
        INDIRECT,
        /**
         * The enderman has teleported away due to a critical hit
         */
        CRITICAL_HIT,
        /**
         * The enderman has teleported away due to the player staring at it during combat
         */
        STARE,
        /**
         * Specific case for CRITICAL_HIT where the enderman is taking rain damage
         */
        DROWN
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
