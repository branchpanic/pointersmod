package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockDetail;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;
import notjoe.pointersmod.common.block.BlockReceiverRedstone;
import notjoe.pointersmod.common.block.ModBlocks;

/**
 * A pointer action that toggles the redstone output of a Redstone Receiver.
 *
 * @see BlockReceiverRedstone
 */
public class PointerActionRedstone extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockDetail blockDetail, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if (world.getBlockState(blockDetail.pos).getBlock() == ModBlocks.receiver_redstone) {
            blockDetail.serializeNbt(stack.getTagCompound());
            return true;
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockDetail blockDetail = new BlockDetail(stack.getTagCompound());
            if (world.getBlockState(blockDetail.pos).getBlock() == ModBlocks.receiver_redstone) {
                world.setBlockState(blockDetail.pos, world.getBlockState(blockDetail.pos)
                    .withProperty(BlockReceiverRedstone.POWERED,
                        !isReceiverPowered(blockDetail.pos, world)));
                world.notifyBlockOfStateChange(blockDetail.pos, ModBlocks.receiver_redstone);
                world.notifyNeighborsOfStateChange(blockDetail.pos, ModBlocks.receiver_redstone);
                return true;
            }
        }
        return false;
    }

    @Override public long getTeslaPerUse() {
        return Config.redstoneTpu;
    }

    private boolean isReceiverPowered(BlockPos pos, World world) {
        return world.getBlockState(pos).getBlock() == ModBlocks.receiver_redstone && world
            .getBlockState(pos).getValue(BlockReceiverRedstone.POWERED);
    }
}
