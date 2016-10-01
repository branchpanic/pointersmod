package notjoe.pointersmod.common.item;

import net.minecraft.item.Item;
import notjoe.pointersmod.api.actions.PointerActionInventory;
import notjoe.pointersmod.api.actions.PointerActionRedstone;
import notjoe.pointersmod.api.actions.PointerActionTeleport;
import notjoe.pointersmod.api.actions.PointerActionWorld;

public class ModItems {
    public static Item pointer_inv;
    public static Item pointer_world;
    public static Item pointer_redstone;
    public static Item pointer_teleport;

    public static void preInit() {
        pointer_inv = new ItemPointerBase("pointer_inv", new PointerActionInventory(false));
        pointer_world = new ItemPointerBase("pointer_world", new PointerActionWorld());
        pointer_redstone = new ItemPointerBase("pointer_redstone", new PointerActionRedstone());
        pointer_teleport = new ItemPointerBase("pointer_teleport", new PointerActionTeleport());
    }
}
