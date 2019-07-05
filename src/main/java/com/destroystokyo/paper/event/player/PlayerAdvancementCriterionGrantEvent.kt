package com.destroystokyo.paper.event.player

import org.bukkit.advancement.Advancement
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

/**
 * Called when a player is granted a criteria in an advancement.
 */
open class PlayerAdvancementCriterionGrantEvent(who: Player,
                                                /**
                                                 * Get the advancement which has been affected.
                                                 *
                                                 * @return affected advancement
                                                 */
                                                val advancement: Advancement,
                                                /**
                                                 * Get the criterion which has been granted.
                                                 *
                                                 * @return granted criterion
                                                 */
                                                val criterion: String) : PlayerEvent(who), Cancellable {
    private var cancel = false

    override fun isCancelled(): Boolean {
        return cancel
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancel = cancel
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
