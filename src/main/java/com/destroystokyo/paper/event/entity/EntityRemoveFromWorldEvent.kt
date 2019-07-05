package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Entity
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired any time an entity is being removed from a world for any reason
 */
open class EntityRemoveFromWorldEvent(entity: Entity) : EntityEvent(entity) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
