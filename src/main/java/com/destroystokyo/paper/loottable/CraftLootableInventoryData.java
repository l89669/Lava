package com.destroystokyo.paper.loottable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CraftLootableInventoryData {

    private static final Random RANDOM = new Random();

    private long lastFill = -1;
    private long nextRefill = -1;
    private int numRefills = 0;
    private Map<UUID, Long> lootedPlayers;
    private final CraftLootableInventory lootable;

    public CraftLootableInventoryData(CraftLootableInventory lootable) {
        this.lootable = lootable;
    }

    long getLastFill() {
        return this.lastFill;
    }

    long getNextRefill() {
        return this.nextRefill;
    }

    long setNextRefill(long nextRefill) {
        long prev = this.nextRefill;
        this.nextRefill = nextRefill;
        return prev;
    }

    CraftLootableInventory getLootable() {
        return lootable;
    }

    public void loadNbt(NBTTagCompound base) {
        if (!base.hasKey("Paper.LootableData", 10)) { // 10 = compound
            return;
        }
        NBTTagCompound comp = base.getCompoundTag("Paper.LootableData");
        if (comp.hasKey("lastFill")) {
            this.lastFill = comp.getLong("lastFill");
        }
        if (comp.hasKey("nextRefill")) {
            this.nextRefill = comp.getLong("nextRefill");
        }

        if (comp.hasKey("numRefills")) {
            this.numRefills = comp.getInteger("numRefills");
        }
        if (comp.hasKey("lootedPlayers", 9)) { // 9 = list
            NBTTagList list = comp.getTagList("lootedPlayers", 10); // 10 = compound
            final int size = list.tagList.size();
            if (size > 0) {
                this.lootedPlayers = new HashMap<>(list.tagList.size());
            }
            for (int i = 0; i < size; i++) {
                final NBTTagCompound cmp = (NBTTagCompound) list.get(i);
                lootedPlayers.put(cmp.getUniqueId("UUID"), cmp.getLong("Time"));
            }
        }
    }

    public void saveNbt(NBTTagCompound base) {
        NBTTagCompound comp = new NBTTagCompound();
        if (this.nextRefill != -1) {
            comp.setLong("nextRefill", this.nextRefill);
        }
        if (this.lastFill != -1) {
            comp.setLong("lastFill", this.lastFill);
        }
        if (this.numRefills != 0) {
            comp.setInteger("numRefills", this.numRefills);
        }
        if (this.lootedPlayers != null && !this.lootedPlayers.isEmpty()) {
            NBTTagList list = new NBTTagList();
            for (Map.Entry<UUID, Long> entry : this.lootedPlayers.entrySet()) {
                NBTTagCompound cmp = new NBTTagCompound();
                cmp.setUniqueId("UUID", entry.getKey());
                cmp.setLong("Time", entry.getValue());
                list.tagList.add(cmp);
            }
            comp.setTag("lootedPlayers", list);
        }

        if (!comp.hasNoTags()) {
            base.setTag("Paper.LootableData", comp);
        }
    }

    void setPlayerLootedState(UUID player, boolean looted) {
        if (looted && this.lootedPlayers == null) {
            this.lootedPlayers = new HashMap<>();
        }
        if (looted) {
            if (!this.lootedPlayers.containsKey(player)) {
                this.lootedPlayers.put(player, System.currentTimeMillis());
            }
        } else if (this.lootedPlayers != null) {
            this.lootedPlayers.remove(player);
        }
    }

    boolean hasPlayerLooted(UUID player) {
        return this.lootedPlayers != null && this.lootedPlayers.containsKey(player);
    }

    Long getLastLooted(UUID player) {
        return lootedPlayers != null ? lootedPlayers.get(player) : null;
    }
}
