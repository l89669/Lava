package com.destroystokyo.paper.event.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.mojang.authlib.GameProfile
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Fires when the server needs to verify if a player is whitelisted.
 *
 * Plugins may override/control the servers whitelist with this event,
 * and dynamically change the kick message.
 *
 */
open class ProfileWhitelistVerifyEvent(
        /**
         * @return The profile of the player trying to connect
         */
        val playerProfile: PlayerProfile,
        /**
         * @return if the server even has whitelist on
         */
        val isWhitelistEnabled: Boolean,
        /**
         * @return Whether the player is whitelisted to play on this server (whitelist may be off is why its true)
         */
        /**
         * Changes the players whitelisted state. false will deny the login
         * @param whitelisted The new whitelisted state
         */
        var isWhitelisted: Boolean,
        /**
         * @return if the player obtained whitelist status by having op
         */
        val isOp: Boolean,
        /**
         * @return the currently planned message to send to the user if they are not whitelisted
         */
        /**
         * @param kickMessage The message to send to the player on kick if not whitelisted. May set to null to use the server configured default
         */
        var kickMessage: String?) : Event() {

    /**
     * @return the gameprofile of the player trying to connect
     */
    val profile: GameProfile
        @Deprecated("Will be removed in 1.13, use #{@link #getPlayerProfile()}")
        get() = playerProfile.gameProfile

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
