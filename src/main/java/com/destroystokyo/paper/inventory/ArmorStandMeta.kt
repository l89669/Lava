package com.destroystokyo.paper.inventory

import org.bukkit.inventory.meta.ItemMeta

interface ArmorStandMeta : ItemMeta {

    /**
     * Gets whether the ArmorStand should be invisible when spawned
     *
     * @return true if this should be invisible
     */
    /**
     * Sets that this ArmorStand should be invisible when spawned
     *
     * @param invisible true if set invisible
     */
    var isInvisible: Boolean

    /**
     * Gets whether this ArmorStand will be small when spawned
     *
     * @return true if it will be small
     */
    /**
     * Sets that this ArmorStand should be small when spawned
     *
     * @param small true if small
     */
    var isSmall: Boolean

    /**
     * Gets whether this ArmorStand will be a marker when spawned
     * The exact details of this flag are an implementation detail
     *
     * @return true if it will be a marker
     */
    /**
     * Sets that this ArmorStand should be a marker when spawned
     * The exact details of this flag are an implementation detail
     *
     * @param marker true if a marker
     */
    var isMarker: Boolean

    /**
     * Gets whether this ArmorStand should have no base plate when spawned
     *
     * @return true if it will not have a base plate
     */
    fun hasNoBasePlate(): Boolean

    /**
     * Gets whether this ArmorStand should show arms when spawned
     *
     * @return true if it will show arms
     */
    fun shouldShowArms(): Boolean

    /**
     * Sets that this ArmorStand should have no base plate when spawned
     *
     * @param noBasePlate true if no base plate
     */
    fun setNoBasePlate(noBasePlate: Boolean)

    /**
     * Sets that this ArmorStand should show arms when spawned
     *
     * @param showArms true if show arms
     */
    fun setShowArms(showArms: Boolean)
}
