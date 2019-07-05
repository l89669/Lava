package com.destroystokyo.paper.event.entity

import org.bukkit.entity.ExperienceOrb
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired anytime the server is about to merge 2 experience orbs into one
 */
open class ExperienceOrbMergeEvent(
        /**
         * @return The orb that will absorb the other experience orb
         */
        val mergeTarget: ExperienceOrb,
        /**
         * @return The orb that is subject to being removed and merged into the target orb
         */
        val mergeSource: ExperienceOrb) : EntityEvent(mergeTarget), Cancellable {

    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    /**
     * @param cancel true if you wish to cancel this event, and prevent the orbs from merging
     */
    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
