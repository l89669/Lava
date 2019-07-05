package com.destroystokyo.paper.profile

import com.google.common.base.Preconditions
import java.util.Objects

/**
 * Represents a property on a [PlayerProfile]
 */
class ProfileProperty @JvmOverloads constructor(name: String, value: String,
                                                /**
                                                 * @return A signature from Mojang for signed properties
                                                 */
                                                val signature: String? = null) {
    /**
     * @return The property name, ie "textures"
     */
    val name: String = Preconditions.checkNotNull(name, "ProfileProperty name can not be null")
    /**
     * @return The property value, likely to be base64 encoded
     */
    val value: String = Preconditions.checkNotNull(value, "ProfileProperty value can not be null")

    /**
     * @return If this property has a signature or not
     */
    val isSigned: Boolean
        get() = this.signature != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ProfileProperty?
        return name == that!!.name &&
                value == that.value &&
                signature == that.signature
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }
}
