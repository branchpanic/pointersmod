package notjoe.pointersmod.common.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

public class ModBlocks {
    public static Block receiver_redstone;

    public static void preInit() {
        GameRegistry.registerTileEntity(TileReceiverRedstone.class, "receiver");

        receiver_redstone = new BlockReceiverRedstone();
    }
}
