package org.spigotmc;

import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class SpigotWorldConfig {

    private final String worldName;
    private final YamlConfiguration config;
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

    private int getAndValidateGrowth(String crop) {
        int modifier = getInt("growth." + crop.toLowerCase(Locale.ENGLISH) + "-modifier", 100);
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

    public float jumpWalkExhaustion;
    public float jumpSprintExhaustion;
    public float combatExhaustion;
    public float regenExhaustion;
    public float swimMultiplier;
    public float sprintMultiplier;
    public float otherMultiplier;

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
}
