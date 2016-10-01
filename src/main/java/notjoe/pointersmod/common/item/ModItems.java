package notjoe.pointersmod.common.item;

import net.minecraft.item.Item;
import notjoe.pointersmod.api.actions.PointerActionInventory;
import notjoe.pointersmod.api.actions.PointerActionRedstone;
import notjoe.pointersmod.api.actions.PointerActionTeleport;
import notjoe.pointersmod.api.actions.PointerActionWorld;
import notjoe.pointersmod.common.Config;

public class ModItems {
    public static Item pointer_inv;
    public static Item pointer_world;
    public static Item pointer_redstone;
    public static Item pointer_teleport;

    public static void preInit() {
        if (Config.enablePointerInventory)
            pointer_inv = new ItemPointerBase("pointer_inv", new PointerActionInventory(false));
        if (Config.enablePointerWorld)
            pointer_world = new ItemPointerBase("pointer_world", new PointerActionWorld());
        if (Config.enablePointerRedstone)
            pointer_redstone = new ItemPointerBase("pointer_redstone", new PointerActionRedstone());
        if (Config.enablePointerTeleport)
            pointer_teleport = new ItemPointerBase("pointer_teleport", new PointerActionTeleport());
    }
}
