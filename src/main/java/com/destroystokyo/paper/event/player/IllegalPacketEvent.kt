package com.destroystokyo.paper.event.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent

open class IllegalPacketEvent(player: Player, val type: String, var kickMessage: String?, e: Exception) : PlayerEvent(player) {
    val exceptionMessage: String? = e.message
    var isShouldKick = true

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()

        @JvmStatic
        fun process(player: Player, type: String, kickMessage: String, exception: Exception) {
            val event = IllegalPacketEvent(player, type, kickMessage, exception)
            event.callEvent()
            if (event.isShouldKick) {
                player.kickPlayer(kickMessage)
            }
            Bukkit.getLogger().severe(player.name + "/" + type + ": " + exception.message)
        }
    }
}
