package com.destroystokyo.paper.event.player

import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.player.PlayerEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Called when the player themselves change their armor items
 *
 *
 * Not currently called for environmental factors though it **MAY BE IN THE FUTURE**
 */
open class PlayerArmorChangeEvent(player: Player,
                             /**
                              * Gets the type of slot being altered.
                              *
                              * @return type of slot being altered
                              */
                             val slotType: SlotType,
                             /**
                              * Gets the existing item that's being replaced
                              *
                              * @return old item
                              */
                             val oldItem: ItemStack,
                             /**
                              * Gets the new item that's replacing the old
                              *
                              * @return new item
                              */
                             val newItem: ItemStack) : PlayerEvent(player) {

    override fun toString(): String {
        return "ArmorChangeEvent{player=$player, slotType=$slotType, oldItem=$oldItem, newItem=$newItem}"
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    enum class SlotType(vararg types: Material) {
        HEAD(DIAMOND_HELMET, GOLD_HELMET, IRON_HELMET, CHAINMAIL_HELMET, LEATHER_HELMET, PUMPKIN, JACK_O_LANTERN),
        CHEST(DIAMOND_CHESTPLATE, GOLD_CHESTPLATE, IRON_CHESTPLATE, CHAINMAIL_CHESTPLATE, LEATHER_CHESTPLATE, ELYTRA),
        LEGS(DIAMOND_LEGGINGS, GOLD_LEGGINGS, IRON_LEGGINGS, CHAINMAIL_LEGGINGS, LEATHER_LEGGINGS),
        FEET(DIAMOND_BOOTS, GOLD_BOOTS, IRON_BOOTS, CHAINMAIL_BOOTS, LEATHER_BOOTS);

        private val mutableTypes = HashSet<Material>()
        private var immutableTypes: Set<Material>? = null

        /**
         * Gets an immutable set of all allowed material types that can be placed in an
         * armor slot.
         *
         * @return immutable set of material types
         */
        val types: Set<Material>?
            get() {
                if (immutableTypes == null) {
                    immutableTypes = Collections.unmodifiableSet(mutableTypes)
                }

                return immutableTypes
            }

        init {
            this.mutableTypes.addAll(listOf(*types))
        }

        companion object {
            /**
             * Gets the type of slot via the specified material
             *
             * @param material material to get slot by
             * @return slot type the material will go in, or null if it won't
             */
            @JvmStatic
            fun getByMaterial(material: Material): SlotType? {
                for (slotType in values()) {
                    if (slotType.types!!.contains(material)) {
                        return slotType
                    }
                }
                return null
            }

            /**
             * Gets whether or not this material can be equipped to a slot
             *
             * @param material material to check
             * @return whether or not this material can be equipped
             */
            @JvmStatic
            fun isEquipable(material: Material): Boolean {
                return getByMaterial(material) != null
            }
        }
    }

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }
}
