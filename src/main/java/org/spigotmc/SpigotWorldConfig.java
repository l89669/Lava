package org.spigotmc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class SpigotWorldConfig {

    private final String worldName;
    private final YamlConfiguration config;
    // Crop growth rates
    public int cactusModifier;
    public int caneModifier;
    public int melonModifier;
    public int mushroomModifier;
    public int pumpkinModifier;
    public int saplingModifier;
    public int wheatModifier;
    public int wartModifier;
    public int vineModifier;
    public int cocoaModifier;
    public double itemMerge;
    public double expMerge;
    public int viewDistance;
    public byte mobSpawnRange;
    public int itemDespawnRate;
    public int animalActivationRange = 32;
    public int monsterActivationRange = 32;
    public int miscActivationRange = 16;
    public int waterActivationRange = 16; // Paper
    public boolean tickInactiveVillagers = true;
    public int playerTrackingRange = 48;
    public int animalTrackingRange = 48;
    public int monsterTrackingRange = 48;
    public int miscTrackingRange = 32;
    public int otherTrackingRange = 64;
    public int hopperTransfer;
    public int hopperCheck;
    public int hopperAmount;
    public boolean randomLightUpdates;
    public boolean saveStructureInfo;
    public int arrowDespawnRate;
    public boolean zombieAggressiveTowardsVillager;
    public boolean nerfSpawnerMobs;
    public boolean enableZombiePigmenPortalSpawns;
    public int dragonDeathSoundRadius;
    public int witherSpawnSoundRadius;
    public int villageSeed;
    public int largeFeatureSeed;
    public int monumentSeed;
    public int slimeSeed;
    public float jumpWalkExhaustion;
    public float jumpSprintExhaustion;
    public float combatExhaustion;
    public float regenExhaustion;
    public float swimMultiplier;
    public float sprintMultiplier;
    public float otherMultiplier;
    public int currentPrimedTnt = 0;
    public int maxTntTicksPerTick;
    public int hangingTickFrequency;
    public int tileMaxTickTime;
    public int entityMaxTickTime;
    public double squidSpawnRangeMin;
    private boolean verbose;

    public SpigotWorldConfig(String worldName) {
        this.worldName = worldName;
        this.config = SpigotConfig.config;
        init();
    }

    public void init() {
        this.verbose = getBoolean("verbose", true);

        log("-------- World Settings For [" + worldName + "] --------");
        SpigotConfig.readConfig(SpigotWorldConfig.class, this);
    }

    private void log(String s) {
        if (verbose) {
            Bukkit.getLogger().info(s);
        }
    }

    private void set(String path, Object val) {
        config.set("world-settings.default." + path, val);
    }

    private boolean getBoolean(String path, boolean def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getBoolean("world-settings." + worldName + "." + path, config.getBoolean("world-settings.default." + path));
    }

    private double getDouble(String path, double def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getDouble("world-settings." + worldName + "." + path, config.getDouble("world-settings.default." + path));
    }

    private int getInt(String path, int def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getInt("world-settings." + worldName + "." + path, config.getInt("world-settings.default." + path));
    }

    private <T> List getList(String path, T def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getList("world-settings." + worldName + "." + path, config.getList("world-settings.default." + path));
    }

    private String getString(String path, String def) {
        config.addDefault("world-settings.default." + path, def);
        return config.getString("world-settings." + worldName + "." + path, config.getString("world-settings.default." + path));
    }

    private int getAndValidateGrowth(String crop) {
        int modifier = getInt("growth." + crop.toLowerCase(java.util.Locale.ENGLISH) + "-modifier", 100);
        if (modifier == 0) {
            log("Cannot set " + crop + " growth to zero, defaulting to 100");
            modifier = 100;
        }
        log(crop + " Growth Modifier: " + modifier + "%");

        return modifier;
    }

    private void growthModifiers() {
        cactusModifier = getAndValidateGrowth("Cactus");
        caneModifier = getAndValidateGrowth("Cane");
        melonModifier = getAndValidateGrowth("Melon");
        mushroomModifier = getAndValidateGrowth("Mushroom");
        pumpkinModifier = getAndValidateGrowth("Pumpkin");
        saplingModifier = getAndValidateGrowth("Sapling");
        wheatModifier = getAndValidateGrowth("Wheat");
        wartModifier = getAndValidateGrowth("NetherWart");
        vineModifier = getAndValidateGrowth("Vine");
        cocoaModifier = getAndValidateGrowth("Cocoa");
    }

    private void itemMerge() {
        itemMerge = getDouble("merge-radius.item", 2.5);
        log("Item Merge Radius: " + itemMerge);
    }

    private void expMerge() {
        expMerge = getDouble("merge-radius.exp", 3.0);
        log("Experience Merge Radius: " + expMerge);
    }

    private void viewDistance() {
        viewDistance = getInt("view-distance", Bukkit.getViewDistance());
        log("View Distance: " + viewDistance);
    }

    private void mobSpawnRange() {
        mobSpawnRange = (byte) getInt("mob-spawn-range", 4);
        log("Mob Spawn Range: " + mobSpawnRange);
    }

    private void itemDespawnRate() {
        itemDespawnRate = getInt("item-despawn-rate", 6000);
        log("Item Despawn Rate: " + itemDespawnRate);
    }

    private void activationRange() {
        animalActivationRange = getInt("entity-activation-range.animals", animalActivationRange);
        monsterActivationRange = getInt("entity-activation-range.monsters", monsterActivationRange);
        miscActivationRange = getInt("entity-activation-range.misc", miscActivationRange);
        waterActivationRange = getInt("entity-activation-range.water", waterActivationRange); // Paper
        tickInactiveVillagers = getBoolean("entity-activation-range.tick-inactive-villagers", tickInactiveVillagers);
        log("Entity Activation Range: An " + animalActivationRange + " / Mo " + monsterActivationRange + " / Mi " + miscActivationRange + " / Tiv " + tickInactiveVillagers);
    }

    private void trackingRange() {
        playerTrackingRange = getInt("entity-tracking-range.players", playerTrackingRange);
        animalTrackingRange = getInt("entity-tracking-range.animals", animalTrackingRange);
        monsterTrackingRange = getInt("entity-tracking-range.monsters", monsterTrackingRange);
        miscTrackingRange = getInt("entity-tracking-range.misc", miscTrackingRange);
        otherTrackingRange = getInt("entity-tracking-range.other", otherTrackingRange);
        log("Entity Tracking Range: Pl " + playerTrackingRange + " / An " + animalTrackingRange + " / Mo " + monsterTrackingRange + " / Mi " + miscTrackingRange + " / Other " + otherTrackingRange);
    }

    private void hoppers() {
        // Set the tick delay between hopper item movements
        hopperTransfer = getInt("ticks-per.hopper-transfer", 8);
        if (SpigotConfig.version < 11) {
            set("ticks-per.hopper-check", 1);
        }
        hopperCheck = getInt("ticks-per.hopper-check", 1);
        hopperAmount = getInt("hopper-amount", 1);
        log("Hopper Transfer: " + hopperTransfer + " Hopper Check: " + hopperCheck + " Hopper Amount: " + hopperAmount);
    }

    private void lightUpdates() {
        randomLightUpdates = getBoolean("random-light-updates", false);
        log("Random Lighting Updates: " + randomLightUpdates);
    }

    private void structureInfo() {
        saveStructureInfo = getBoolean("save-structure-info", true);
        log("Structure Info Saving: " + saveStructureInfo);
        if (!saveStructureInfo) {
            log("*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs!");
            log("*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!");
        }
    }

    private void arrowDespawnRate() {
        arrowDespawnRate = getInt("arrow-despawn-rate", 1200);
        log("Arrow Despawn Rate: " + arrowDespawnRate);
    }

    private void zombieAggressiveTowardsVillager() {
        zombieAggressiveTowardsVillager = getBoolean("zombie-aggressive-towards-villager", true);
        log("Zombie Aggressive Towards Villager: " + zombieAggressiveTowardsVillager);
    }

    private void nerfSpawnerMobs() {
        nerfSpawnerMobs = getBoolean("nerf-spawner-mobs", false);
        log("Nerfing mobs spawned from spawners: " + nerfSpawnerMobs);
    }

    private void enableZombiePigmenPortalSpawns() {
        enableZombiePigmenPortalSpawns = getBoolean("enable-zombie-pigmen-portal-spawns", true);
        log("Allow Zombie Pigmen to spawn from portal blocks: " + enableZombiePigmenPortalSpawns);
    }

    private void keepDragonDeathPerWorld() {
        dragonDeathSoundRadius = getInt("dragon-death-sound-radius", 0);
    }

    private void witherSpawnSoundRadius() {
        witherSpawnSoundRadius = getInt("wither-spawn-sound-radius", 0);
    }

    private void initWorldGenSeeds() {
        villageSeed = getInt("seed-village", 10387312);
        largeFeatureSeed = getInt("seed-feature", 14357617);
        monumentSeed = getInt("seed-monument", 10387313);
        slimeSeed = getInt("seed-slime", 987234911);
        log("Custom Map Seeds:  Village: " + villageSeed + " Feature: " + largeFeatureSeed + " Monument: " + monumentSeed + " Slime: " + slimeSeed);
    }

    private void initHunger() {
        if (SpigotConfig.version < 10) {
            set("hunger.walk-exhaustion", null);
            set("hunger.sprint-exhaustion", null);
            set("hunger.combat-exhaustion", 0.1);
            set("hunger.regen-exhaustion", 6.0);
        }

        jumpWalkExhaustion = (float) getDouble("hunger.jump-walk-exhaustion", 0.05);
        jumpSprintExhaustion = (float) getDouble("hunger.jump-sprint-exhaustion", 0.2);
        combatExhaustion = (float) getDouble("hunger.combat-exhaustion", 0.1);
        regenExhaustion = (float) getDouble("hunger.regen-exhaustion", 6.0);
        swimMultiplier = (float) getDouble("hunger.swim-multiplier", 0.01);
        sprintMultiplier = (float) getDouble("hunger.sprint-multiplier", 0.1);
        otherMultiplier = (float) getDouble("hunger.other-multiplier", 0.0);
    }

    private void maxTntPerTick() {
        if (SpigotConfig.version < 7) {
            set("max-tnt-per-tick", 100);
        }
        maxTntTicksPerTick = getInt("max-tnt-per-tick", 100);
        log("Max TNT Explosions: " + maxTntTicksPerTick);
    }

    private void hangingTickFrequency() {
        hangingTickFrequency = getInt("hanging-tick-frequency", 100);
    }

    private void maxTickTimes() {
        tileMaxTickTime = getInt("max-tick-time.tile", 50);
        entityMaxTickTime = getInt("max-tick-time.entity", 50);
        log("Tile Max Tick Time: " + tileMaxTickTime + "ms Entity max Tick Time: " + entityMaxTickTime + "ms");
    }

    private void squidSpawnRange() {
        squidSpawnRangeMin = getDouble("squid-spawn-range.min", 45.0D);
    }
}
