package org.bukkit.inventory

interface ArmoredHorseInventory : AbstractHorseInventory {

    /**
     * Gets the item in the horse's armor slot.
     *
     * @return the armor item
     */
    /**
     * Sets the item in the horse's armor slot.
     *
     * @param stack the new item
     */
    var armor: ItemStack
}
