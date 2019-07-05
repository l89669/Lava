package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.Projectile
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent

/**
 * Called when an projectile collides with an entity
 *
 *
 * This event is called **before** [org.bukkit.event.entity.EntityDamageByEntityEvent], and cancelling it will allow the projectile to continue flying
 */
open class ProjectileCollideEvent(what: Projectile,
                             /**
                              * Get the entity the projectile collided with
                              *
                              * @return the entity collided with
                              */
                             val collidedWith: Entity) : EntityEvent(what), Cancellable {

    private var cancelled = false

    /**
     * Get the projectile that collided
     *
     * @return the projectile that collided
     */
    override fun getEntity(): Projectile {
        return super.getEntity() as Projectile
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        this.cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
