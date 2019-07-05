package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent
import org.bukkit.util.Vector

/**
 * Fired when an Entity is knocked back by the hit of another Entity. The acceleration
 * vector can be modified. If this event is cancelled, the entity is not knocked back.
 *
 */
open class EntityKnockbackByEntityEvent(entity: LivingEntity,
                                   /**
                                    * @return the Entity which hit
                                    */
                                   val hitBy: Entity,
                                   /**
                                    * @return the original knockback strength.
                                    */
                                   val knockbackStrength: Float,
                                   /**
                                    * @return the acceleration that will be applied
                                    */
                                   val acceleration: Vector) : EntityEvent(entity), Cancellable {
    private var cancelled = false

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    override fun isCancelled(): Boolean {
        return cancelled
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    /**
     * @return the entity which was knocked back
     */
    override fun getEntity(): LivingEntity {
        return super.getEntity() as LivingEntity
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
