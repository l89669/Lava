package com.destroystokyo.paper.event.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Fired when the server is requesting to fill in properties of an incomplete profile, such as textures.
 *
 * Allows plugins to pre populate cached properties and avoid a call to the Mojang API
 */
open class PreFillProfileEvent(
        /**
         * @return The profile that needs its properties filled
         */
        val playerProfile: PlayerProfile) : Event(!Bukkit.isPrimaryThread()) {

    /**
     * Sets the properties on the profile, avoiding the call to the Mojang API
     * Same as .getPlayerProfile().setProperties(properties);
     *
     * @see PlayerProfile.setProperties
     * @param properties The properties to set/append
     */
    fun setProperties(properties: Collection<ProfileProperty>) {
        playerProfile.setProperties(properties)
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
