package org.bukkit.craftbukkit.block;

import net.minecraft.inventory.IInventory;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

//Cauldron code
public class CraftCustomContainer extends CraftBlockState implements InventoryHolder {
    private final CraftWorld world;
    private final IInventory container;

    public CraftCustomContainer(Block block) {
        super(block);
        world = (CraftWorld) block.getWorld();
        container = (IInventory) world.getTileEntityAt(getX(), getY(), getZ());
    }

    @Override
    public Inventory getInventory() {
        CraftInventory inventory = new CraftInventory(container);
        return inventory;
    }
}
