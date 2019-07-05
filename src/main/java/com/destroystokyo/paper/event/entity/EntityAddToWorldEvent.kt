package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Entity
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired any time an entity is being added to the world for any reason.
 *
 *
 * Not to be confused with [org.bukkit.event.entity.CreatureSpawnEvent]
 * This will fire anytime a chunk is reloaded too.
 */
open class EntityAddToWorldEvent(entity: Entity) : EntityEvent(entity) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
