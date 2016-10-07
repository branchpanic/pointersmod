package notjoe.pointersmod.common;

import net.minecraftforge.common.config.Configuration;
import notjoe.pointersmod.PointersMod;

public class Config {
    private static final String CATEGORY_GENERAL = "general";

    public static int pointerOperatingRange = 0;

    public static boolean addCraftingRecipes = true;

    public static boolean enablePointerInventory = true;
    public static boolean enablePointerRedstone = true;
    public static boolean enablePointerTeleport = true;
    public static boolean enablePointerWorld = true;

    public static int pointerCapacity = 100000;
    public static int inventoryTpu = 1000;
    public static int teleportTpu = 5000;
    public static int worldTpu = 100;
    public static int redstoneTpu = 80;

    public static void initConfigValues() {
        Configuration c = PointersMod.config;
        try {
            c.load();
            readGeneralValues(c);
        } catch (Exception e) {
            PointersMod.logger.error("Could not load config file! Using default values.");
        } finally {
            if (c.hasChanged()) {
                c.save();
            }
        }
    }

    private static void readGeneralValues(Configuration c) {
        c.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration.");
        pointerOperatingRange =
            c.getInt("pointerOperatingRange", CATEGORY_GENERAL, Config.pointerOperatingRange, 0,
                Integer.MAX_VALUE,
                "In order to use a Pointer, the player must be within this many blocks of its target (0 to disable).");
        pointerCapacity =
            c.getInt("pointerCapacity", CATEGORY_GENERAL, Config.pointerCapacity, 1000,
                Integer.MAX_VALUE, "How much Tesla energy a pointer can hold.");
        inventoryTpu =
            c.getInt("inventoryTpu", CATEGORY_GENERAL, Config.inventoryTpu, 0, Integer.MAX_VALUE,
                "How much Tesla energy is used every time an Inventory Pointer is used.");
        teleportTpu =
            c.getInt("teleportTpu", CATEGORY_GENERAL, Config.teleportTpu, 0, Integer.MAX_VALUE,
                "How much Tesla energy is used every time a Positional Pointer is used.");
        worldTpu = c.getInt("worldTpu", CATEGORY_GENERAL, Config.worldTpu, 0, Integer.MAX_VALUE,
            "How much Tesla energy is used every time a Mechanical Pointer is used.");
        redstoneTpu =
            c.getInt("redstoneTpu", CATEGORY_GENERAL, Config.redstoneTpu, 0, Integer.MAX_VALUE,
                "How much Tesla energy is used every time a Redstone Pointer is used.");
        enablePointerInventory =
            c.getBoolean("enablePointerInventory", CATEGORY_GENERAL, Config.enablePointerInventory,
                "Determines whether or not the Inventory Pointer is enabled.");
        enablePointerRedstone =
            c.getBoolean("enablePointerRedstone", CATEGORY_GENERAL, Config.enablePointerRedstone,
                "Determines whether or not the Redstone Pointer is enabled.");
        enablePointerTeleport =
            c.getBoolean("enablePointerTeleport", CATEGORY_GENERAL, Config.enablePointerTeleport,
                "Determines whether or not the Positional Pointer is enabled.");
        enablePointerWorld =
            c.getBoolean("enablePointerWorld", CATEGORY_GENERAL, Config.enablePointerWorld,
                "Determines whether or not the Mechanical Pointer is enabled.");
        addCraftingRecipes =
            c.getBoolean("addCraftingRecipes", CATEGORY_GENERAL, Config.addCraftingRecipes,
                "Determines whether or not the mod should add its own crafting recipes and components.");
    }
}