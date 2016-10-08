package notjoe.pointersmod.common;

import net.minecraftforge.common.config.Configuration;
import notjoe.pointersmod.PointersMod;

public class Config {
    private static final String CATEGORY_GENERAL = "general";
    private static final String CATEGORY_UPDATES = "updates";

    public static int pointerOperatingRange = 0;

    public static boolean addCraftingRecipes = true;

    public static boolean enablePointerInventory = true;
    public static boolean enablePointerRedstone = true;
    public static boolean enablePointerTeleport = true;
    public static boolean enablePointerWorld = true;
    public static boolean enablePointerFluid = true;

    public static boolean enableUpdateCheck = true;
    public static String updateChannel = "alpha";

    public static int pointerCapacity = 100000;
    public static int inventoryTpu = 1000;
    public static int teleportTpu = 5000;
    public static int worldTpu = 100;
    public static int redstoneTpu = 80;
    public static int fluidTpu = 500;

    public static int fluidPointerTransfer = 1000;

    public static void initConfigValues() {
        Configuration c = PointersMod.config;
        try {
            c.load();
            readGeneralValues(c);
            readUpdateValues(c);
        } catch (Exception e) {
            PointersMod.logger.error("Could not load config file! Using default values.");
        } finally {
            if (c.hasChanged()) {
                c.save();
            }
        }
    }

    private static void readUpdateValues(Configuration c) {
        c.addCustomCategoryComment(CATEGORY_UPDATES, "Update checking settings.");
        enableUpdateCheck = c.getBoolean("updateCheck", CATEGORY_UPDATES, true,
            "Determines whether or not Pointers should check for updates on game startup.");
        updateChannel = c.getString("updateChannel", CATEGORY_UPDATES, "alpha",
            "Determines what type of release to check for updates for.",
            new String[] {"alpha", "beta", "release"});
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
        fluidTpu = c.getInt("fluidTpu", CATEGORY_GENERAL, Config.fluidTpu, 0, Integer.MAX_VALUE,
            "How much Tesla energy is used every time a Fluid Pointer is used.");
        fluidPointerTransfer =
            c.getInt("fluidTransfer", CATEGORY_GENERAL, Config.fluidPointerTransfer, 0,
                Integer.MAX_VALUE,
                "How many mB of fluid a fluid pointer will transfer every time it is used. No more, no less. (Buckets will not work with values != to 1000");
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
        enablePointerFluid =
            c.getBoolean("enablePointerFluid", CATEGORY_GENERAL, Config.enablePointerFluid,
                "Determines whether or not the Fluid Pointer is enabled.");
        addCraftingRecipes =
            c.getBoolean("addCraftingRecipes", CATEGORY_GENERAL, Config.addCraftingRecipes,
                "Determines whether or not the mod should add its own crafting recipe and components.");
    }
}
