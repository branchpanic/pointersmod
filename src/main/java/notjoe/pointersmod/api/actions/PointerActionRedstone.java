package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

public class PointerActionRedstone extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if (world.getBlockState(blockInWorld.pos).getBlock() == ModBlocks.receiver_redstone) {
            blockInWorld.serializeNbt(stack.getTagCompound());
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = new BlockInWorld(stack.getTagCompound());
            if (world.getTileEntity(blockInWorld.pos) != null && world
                .getTileEntity(blockInWorld.pos) instanceof TileReceiverRedstone) {
                TileReceiverRedstone receiver =
                    (TileReceiverRedstone) world.getTileEntity(blockInWorld.pos);
                receiver.togglePower();
                world.notifyNeighborsOfStateChange(blockInWorld.pos, ModBlocks.receiver_redstone);
                return true;
            }
        }
        return false;
    }
}
