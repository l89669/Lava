package com.destroystokyo.paper.event.profile

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import com.mojang.authlib.properties.Property
import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

/**
 * Allows a plugin to intercept a Profile Lookup for a Profile by name
 *
 * At the point of event fire, the UUID and properties are unset.
 *
 * If a plugin sets the UUID, and optionally the properties, the API call to look up the profile may be skipped.
 *
 * No guarantees are made about thread execution context for this event. If you need to know, check
 * event.isAsync()
 */
open class PreLookupProfileEvent(
        /**
         * @return Name of the profile
         */
        val name: String) : Event(!Bukkit.isPrimaryThread()) {
    /**
     * If this value is left null by the completion of the event call, then the server will
     * trigger a call to the Mojang API to look up the UUID (Network Request), and subsequently, fire a
     * [LookupProfileEvent]
     *
     * @return The UUID of the profile if it has already been provided by a plugin
     */
    /**
     * Sets the UUID for this player name. This will skip the initial API call to find the players UUID.
     *
     * However, if Profile Properties are needed by the server, you must also set them or else an API call might still be made.
     *
     * @param uuid the UUID to set for the profile or null to reset
     */
    var uuid: UUID? = null
    private var properties: MutableSet<ProfileProperty> = HashSet()

    /**
     * @return The currently pending prepopulated properties.
     * Any property in this Set will be automatically prefilled on this Profile
     */
    /**
     * Clears any existing prepopulated properties and uses the supplied properties
     * Any property in this Set will be automatically prefilled on this Profile
     * @param properties The properties to add
     */
    var profileProperties: Set<ProfileProperty>
        get() = this.properties
        set(properties) {
            this.properties = HashSet()
            this.properties.addAll(properties)
        }

    /**
     * Get the properties for this profile
     *
     * @return the property map to attach to the new [PlayerProfile]
     */
    @Deprecated("will be removed with 1.13  Use {@link #getProfileProperties()}")
    fun getProperties(): Multimap<String, Property> {
        val props = ArrayListMultimap.create<String, Property>()

        for (property in properties) {
            props.put(property.name, Property(property.name, property.value, property.signature))
        }
        return props
    }

    /**
     * Completely replaces all Properties with the new provided properties
     * @param properties the properties to set on the new profile
     */
    @Deprecated("will be removed with 1.13 Use {@link #setProfileProperties(Set)}")
    fun setProperties(properties: Multimap<String, Property>) {
        this.properties = HashSet()
        properties.values().forEach { property -> this.properties.add(ProfileProperty(property.name, property.value, property.signature)) }
    }

    /**
     * Adds additional properties, without removing the original properties
     * @param properties the properties to add to the existing properties
     */
    @Deprecated("will be removed with 1.13 use {@link #addProfileProperties(Set)}")
    fun addProperties(properties: Multimap<String, Property>) {
        properties.values().forEach { property -> this.properties.add(ProfileProperty(property.name, property.value, property.signature)) }
    }

    /**
     * Adds any properties currently missing to the prepopulated properties set, replacing any that already were set.
     * Any property in this Set will be automatically prefilled on this Profile
     * @param properties The properties to add
     */
    fun addProfileProperties(properties: Set<ProfileProperty>) {
        this.properties.addAll(properties)
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
