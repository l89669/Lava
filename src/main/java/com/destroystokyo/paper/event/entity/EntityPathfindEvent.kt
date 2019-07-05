package com.destroystokyo.paper.event.entity

import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Fired when an Entity decides to start moving towards a location.
 *
 *
 * This event does not fire for the entities actual movement. Only when it
 * is choosing to start moving to a location.
 */
open class EntityPathfindEvent(entity: Entity,
                          /**
                           * The Location of where the entity is about to move to.
                           *
                           *
                           * Note that if the target happened to of been an entity
                           *
                           * @return Location of where the entity is trying to pathfind to.
                           */
                          val loc: Location,
                          /**
                           * If the Entity is trying to pathfind to an entity, this is the entity in relation.
                           *
                           *
                           * Otherwise this will return null.
                           *
                           * @return The entity target or null
                           */
                          val targetEntity: Entity) : EntityEvent(entity), Cancellable {

    private var cancelled = false

    /**
     * The Entity that is pathfinding.
     *
     * @return The Entity that is pathfinding.
     */
    override fun getEntity(): Entity {
        return entity
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
