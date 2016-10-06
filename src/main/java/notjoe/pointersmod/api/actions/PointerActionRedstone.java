package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;
import notjoe.pointersmod.common.block.BlockReceiverRedstone;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

public class PointerActionRedstone extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if (world.getBlockState(blockInWorld.pos).getBlock() == ModBlocks.receiver_redstone) {
            blockInWorld.serializeNbt(stack.getTagCompound());
            return true;
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = new BlockInWorld(stack.getTagCompound());
            if (world.getBlockState(blockInWorld.pos).getBlock() == ModBlocks.receiver_redstone) {
                world.setBlockState(blockInWorld.pos, world.getBlockState(blockInWorld.pos).withProperty(
                    BlockReceiverRedstone.POWERED, true));
                world.notifyBlockOfStateChange(blockInWorld.pos, ModBlocks.receiver_redstone);
                world.notifyNeighborsOfStateChange(blockInWorld.pos, ModBlocks.receiver_redstone);
                return true;
            }
        }
        return false;
    }

    @Override public long getTeslaPerUse() {
        return Config.redstoneTpu;
    }
}
