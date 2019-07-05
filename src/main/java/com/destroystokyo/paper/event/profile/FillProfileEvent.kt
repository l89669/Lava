package com.destroystokyo.paper.event.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Fired once a profiles additional properties (such as textures) has been filled
 */
open class FillProfileEvent(
        /**
         * @return The Profile that had properties filled
         */
        val playerProfile: PlayerProfile) : Event(!org.bukkit.Bukkit.isPrimaryThread()) {

    /**
     * Same as .getPlayerProfile().getProperties()
     *
     * @see PlayerProfile.getProperties
     * @return The new properties on the profile.
     */
    val properties: Set<ProfileProperty>
        get() = playerProfile.properties

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
