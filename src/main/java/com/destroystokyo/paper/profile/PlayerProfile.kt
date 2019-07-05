package com.destroystokyo.paper.profile

import com.mojang.authlib.GameProfile
import java.util.*

/**
 * Represents a players profile for the game, such as UUID, Name, and textures.
 */
interface PlayerProfile {

    /**
     * @return The players name, if set
     */
    val name: String?

    /**
     * @return The players unique identifier, if set
     */
    val id: UUID?

    /**
     * @return A Mutable set of this players properties, such as textures.
     * Values specified here are subject to implementation details.
     */
    val properties: Set<ProfileProperty>

    /**
     * @return If the profile is now complete (has UUID and Name)
     */
    val isComplete: Boolean

    /**
     * @return the GameProfile for this PlayerProfile
     */
    @get:Deprecated("Will be removed in 1.13\n" +
            "      ")
    val gameProfile: GameProfile

    /**
     * Sets this profiles Name
     *
     * @param name The new Name
     * @return The previous Name
     */
    fun setName(name: String?): String

    /**
     * Sets this profiles UUID
     *
     * @param uuid The new UUID
     * @return The previous UUID
     */
    fun setId(uuid: UUID?): UUID

    /**
     * Check if the Profile has the specified property
     * @param property Property name to check
     * @return If the property is set
     */
    fun hasProperty(property: String): Boolean

    /**
     * Sets a property. If the property already exists, the previous one will be replaced
     * @param property Property to set.
     */
    fun setProperty(property: ProfileProperty)

    /**
     * Sets multiple properties. If any of the set properties already exist, it will be replaced
     * @param properties The properties to set
     */
    fun setProperties(properties: Collection<ProfileProperty>)

    /**
     * Removes a specific property from this profile
     * @param property The property to remove
     * @return If a property was removed
     */
    fun removeProperty(property: String): Boolean

    /**
     * Removes a specific property from this profile
     * @param property The property to remove
     * @return If a property was removed
     */
    fun removeProperty(property: ProfileProperty): Boolean {
        return removeProperty(property.name)
    }

    /**
     * Removes all properties in the collection
     * @param properties The properties to remove
     * @return If any property was removed
     */
    fun removeProperties(properties: Collection<ProfileProperty>): Boolean {
        var removed = false
        for (property in properties) {
            if (removeProperty(property)) {
                removed = true
            }
        }
        return removed
    }

    /**
     * Clears all properties on this profile
     */
    fun clearProperties()

    /**
     * Like [.complete] but will try only from cache, and not make network calls
     * Does not account for textures.
     *
     * @return If the profile is now complete (has UUID and Name)
     */
    fun completeFromCache(): Boolean

    /**
     * If this profile is not complete, then make the API call to complete it.
     * This is a blocking operation and should be done asynchronously.
     *
     * This will also complete textures. If you do not want to load textures, use {[.complete]}
     * @return If the profile is now complete (has UUID and Name) (if you get rate limited, this operation may fail)
     */
    fun complete(): Boolean {
        return complete(true)
    }

    /**
     * If this profile is not complete, then make the API call to complete it.
     * This is a blocking operation and should be done asynchronously.
     *
     * Optionally will also fill textures.
     * @param textures controls if we should fill the profile with texture properties
     * @return If the profile is now complete (has UUID and Name) (if you get rate limited, this operation may fail)
     */
    fun complete(textures: Boolean): Boolean

    /**
     * Whether or not this Profile has textures associated to it
     * @return If has a textures property
     */
    fun hasTextures(): Boolean {
        return hasProperty("textures")
    }
}
