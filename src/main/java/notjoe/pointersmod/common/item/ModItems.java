package notjoe.pointersmod.common.item;

import net.minecraft.item.Item;
import notjoe.pointersmod.api.actions.*;
import notjoe.pointersmod.common.Config;

public class ModItems {
    public static Item pointer_inv;
    public static Item pointer_world;
    public static Item pointer_redstone;
    public static Item pointer_teleport;
    public static Item pointer_fluid;

    public static Item component;

    public static void preInit() {
        if (Config.enablePointerInventory)
            pointer_inv = new ItemPointerBase("pointer_inv", new PointerActionInventory(false));
        if (Config.enablePointerWorld)
            pointer_world = new ItemPointerBase("pointer_world", new PointerActionWorld());
        if (Config.enablePointerRedstone)
            pointer_redstone = new ItemPointerBase("pointer_redstone", new PointerActionRedstone());
        if (Config.enablePointerTeleport)
            pointer_teleport = new ItemPointerBase("pointer_teleport", new PointerActionTeleport());
        if (Config.enablePointerFluid)
            pointer_fluid = new ItemPointerBase("pointer_fluid", new PointerActionFluid());

        if (Config.addCraftingRecipes)
            component = new ItemComponent();
    }
}
