package com.destroystokyo.paper.event.entity

import org.bukkit.entity.Witch
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent
import org.bukkit.inventory.ItemStack

/**
 * Fired when a witch consumes the potion in their hand to buff themselves.
 */
open class WitchConsumePotionEvent(witch: Witch, private var potion: ItemStack?) : EntityEvent(witch), Cancellable {

    private var cancelled = false

    override fun getEntity(): Witch {
        return super.getEntity() as Witch
    }

    /**
     * @return the potion the witch will consume and have the effects applied.
     */
    fun getPotion(): ItemStack? {
        return potion
    }

    /**
     * Sets the potion to be consumed and applied to the witch.
     * @param potion The potion
     */
    fun setPotion(potion: ItemStack?) {
        this.potion = potion?.clone()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    /**
     * @return Event was cancelled or potion was null
     */
    override fun isCancelled(): Boolean {
        return cancelled || potion == null
    }

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
