package com.destroystokyo.paper.event.player

import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.EquipmentSlot

open class PlayerUseUnknownEntityEvent(who: Player, val entityId: Int, val isAttack: Boolean, val hand: EquipmentSlot) : PlayerEvent(who) {

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
