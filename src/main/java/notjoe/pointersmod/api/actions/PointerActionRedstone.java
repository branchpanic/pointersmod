package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

import java.util.List;

public class PointerActionRedstone implements PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if(world.getBlockState(blockInWorld.pos).getBlock() == ModBlocks.receiver_redstone) {
            blockInWorld.serializeNbt(stack.getTagCompound());
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = new BlockInWorld(stack.getTagCompound());
            if(world.getTileEntity(blockInWorld.pos) != null && world.getTileEntity(blockInWorld.pos) instanceof TileReceiverRedstone) {
                TileReceiverRedstone receiver = (TileReceiverRedstone) world.getTileEntity(blockInWorld.pos);
                receiver.togglePower();
                world.notifyNeighborsOfStateChange(blockInWorld.pos, ModBlocks.receiver_redstone);
                return true;
            }
        }
        return false;
    }

    @Override public List<String> getExtraInfo(ItemStack stack) {
        return null;
    }

    @Override public BlockInWorld getPointerTarget(ItemStack stack) {
        return new BlockInWorld(stack.getTagCompound());
    }

    @Override public boolean hasTarget(ItemStack stack) {
        return NbtHelper.stackHasBlockData(stack);
    }

    @Override public boolean isTargetAccessible(ItemStack stack, World world, EntityPlayer player) {
        BlockInWorld target = new BlockInWorld(stack.getTagCompound());
        return world.isBlockLoaded(target.pos) &&
            player.canPlayerEdit(target.pos, target.facing, stack) &&
            player.capabilities.allowEdit;
    }
}
