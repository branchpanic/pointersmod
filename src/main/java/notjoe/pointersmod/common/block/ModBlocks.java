package notjoe.pointersmod.common.block;

import net.minecraft.block.Block;

public class ModBlocks {
    public static Block receiver_redstone;
    public static void preInit() {
        receiver_redstone = new BlockReceiverRedstone();
    }
}
