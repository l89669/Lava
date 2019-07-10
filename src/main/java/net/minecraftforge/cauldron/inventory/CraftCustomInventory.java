package net.minecraftforge.cauldron.inventory;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.*;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import javax.annotation.Nullable;

public class CraftCustomInventory implements InventoryHolder {

    private final IInventory inventory;
    private final CraftInventory container;

    public CraftCustomInventory(IInventory inventory) {
        this.container = new CraftInventory(inventory);
        this.inventory = inventory;
    }

    public CraftCustomInventory(ItemStackHandler handler) {
        this.container = new CraftInventoryCustom(this, handler.getStacks());
        this.inventory = this.container.getInventory();
    }

    @Nullable
    public static InventoryHolder holderFromForge(IItemHandler handler) {
        if (handler == null) {
            return null;
        }
        if (handler instanceof ItemStackHandler) {
            return new CraftCustomInventory((ItemStackHandler) handler);
        }
        if (handler instanceof SlotItemHandler) {
            return new CraftCustomInventory(((SlotItemHandler) handler).inventory);
        }
        if (handler instanceof InvWrapper) {
            return new CraftCustomInventory(((InvWrapper) handler).getInv());
        }
        if (handler instanceof SidedInvWrapper) {
            return new CraftCustomInventory(((SidedInvWrapper) handler).getInv());
        }
        if (handler instanceof PlayerInvWrapper) {
            return new CraftCustomInventory(getPlayerInv((PlayerInvWrapper) handler));
        }
        return null;
    }

    public static InventoryPlayer getPlayerInv(PlayerInvWrapper inv) {
        IItemHandlerModifiable[] piw = ObfuscationReflectionHelper.getPrivateValue(CombinedInvWrapper.class, inv, "itemHandler");
        for (IItemHandlerModifiable itemHandler : piw) {
            if (itemHandler instanceof PlayerMainInvWrapper) {
                return ((PlayerMainInvWrapper) itemHandler).getInventoryPlayer();
            }
            if (itemHandler instanceof PlayerArmorInvWrapper) {
                return ((PlayerArmorInvWrapper) itemHandler).getInventoryPlayer();
            }
        }
        return null;
    }

    @Nullable
    public static Inventory inventoryFromForge(IItemHandler handler) {
        InventoryHolder holder = holderFromForge(handler);
        return holder != null ? holder.getInventory() : null;
    }

    @Override
    public Inventory getInventory() {
        return this.container;
    }
}
