package com.destroystokyo.paper.loottable

import org.bukkit.block.Block

interface LootableBlockInventory : LootableInventory {

    /**
     * Gets the block that is lootable
     *
     * @return The Block
     */
    val block: Block
}
