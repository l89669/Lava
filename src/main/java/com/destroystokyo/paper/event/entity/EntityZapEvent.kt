package com.destroystokyo.paper.event.entity

import org.apache.commons.lang.Validate
import org.bukkit.entity.Entity
import org.bukkit.entity.LightningStrike
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired when lightning strikes an entity
 */
open class EntityZapEvent(entity: Entity,
                     /**
                      * Gets the lightning bolt that is striking the entity.
                      * @return The lightning bolt responsible for this event
                      */
                     val bolt: LightningStrike,
                     /**
                      * Gets the entity that will replace the struck entity.
                      * @return The entity that will replace the struck entity
                      */
                     val replacementEntity: Entity) : EntityEvent(entity), Cancellable {
    private var cancelled: Boolean = false

    init {
        Validate.notNull(bolt)
        Validate.notNull(replacementEntity)
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
