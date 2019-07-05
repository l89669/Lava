package com.destroystokyo.paper.event.entity

import org.bukkit.Material
import org.bukkit.entity.Witch
import org.bukkit.event.Cancellable
import org.bukkit.event.HandlerList
import org.bukkit.event.entity.EntityEvent
import org.bukkit.inventory.ItemStack

open class WitchReadyPotionEvent(witch: Witch, private var potion: ItemStack?) : EntityEvent(witch), Cancellable {

    private var cancelled = false

    override fun getEntity(): Witch {
        return super.getEntity() as Witch
    }

    /**
     * @return the potion the witch is readying to use
     */
    fun getPotion(): ItemStack? {
        return potion
    }

    /**
     * Sets the potion the which is going to hold and use
     * @param potion The potion
     */
    fun setPotion(potion: ItemStack?) {
        this.potion = potion?.clone()
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
        /**
         * Fires thee event, returning the desired potion, or air of cancelled
         * @param witch the witch whom is readying to use a potion
         * @param potion the potion to be used
         * @return The ItemStack to be used
         */
        @JvmStatic
        fun process(witch: Witch, potion: ItemStack): ItemStack? {
            val event = WitchReadyPotionEvent(witch, potion)
            return if (!event.callEvent() || event.getPotion() == null) {
                ItemStack(Material.AIR)
            } else event.getPotion()
        }

        @JvmStatic
        val handlerList = HandlerList()
    }
}
