package com.destroystokyo.paper.loottable

/**
 * Defines an object that has a Loot Table and seed associated with it.
 *
 * How the Loot Table and seed are used may vary based on Minecraft Versions
 * and what type of object is using the Loot Table
 */
interface Lootable {

    /**
     * Gets the name of the Loot Table to be used in the World Folder
     * @return The name, or null if no loot table exists
     */
    val lootTableName: String?

    /**
     * Gets the current seed associated to the Loot Table on this object
     *
     * @return The seed, or 0 for random
     */
    val lootTableSeed: Long

    /**
     * Returns whether or not this object has a Loot Table
     * @return Has a loot table
     */
    fun hasLootTable(): Boolean {
        return lootTableName != null
    }

    /**
     * Sets the name of the Loot Table to be used in the World Folder
     * Will use a random seed (0)
     *
     * @param name name in either foo or minecraft:foo format
     * @return The previous Loot Table before the change
     */
    fun setLootTable(name: String): String {
        return setLootTable(name, 0)
    }

    /**
     * Sets the name of the Loot Table to be used in the World Folder
     * Uses supplied Seed
     *
     * @param name name in either foo or minecraft:foo format
     * @param seed seed for the loot table. If 0, seed will be random
     * @return The previous Loot Table before the change
     */
    fun setLootTable(name: String, seed: Long): String

    /**
     * Changes the current seed associated with the Loot Table on this object.
     *
     * The seed will have no affect if this object does not have a Loot Table
     * associated with it.
     *
     * @throws IllegalStateException If called when this object does not have a loot table
     * @param seed The seed to use, or 0 for random
     * @return The previous seed
     */
    fun setLootTableSeed(seed: Long): Long {
        val lootTableName = lootTableName
                ?: throw IllegalStateException("This object does not currently have a Loot Table.")

        val prev = lootTableSeed
        setLootTable(lootTableName, seed)
        return prev
    }

    /**
     * Clears the associated Loot Table to this object
     */
    fun clearLootTable()
}
