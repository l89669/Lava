package com.destroystokyo.paper.event.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.mojang.authlib.GameProfile
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Allows a plugin to be notified anytime AFTER a Profile has been looked up from the Mojang API
 * This is an opportunity to view the response and potentially cache things.
 *
 * No guarantees are made about thread execution context for this event. If you need to know, check
 * event.isAsync()
 */
open class LookupProfileEvent(
        /**
         * @return The profile that was recently looked up. This profile can be mutated
         */
        val playerProfile: PlayerProfile) : Event(!Bukkit.isPrimaryThread()) {

    /**
     * @return The profile that was recently looked up. This profile can be mutated
     */
    val profile: GameProfile
        @Deprecated("will be removed with 1.13, use {@link #getPlayerProfile()}")
        get() = playerProfile.gameProfile

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
