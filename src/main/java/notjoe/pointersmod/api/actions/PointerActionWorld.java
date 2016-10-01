package notjoe.pointersmod.api.actions;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;

import java.util.List;

public class PointerActionWorld implements PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        blockInWorld.serializeNbt(stack.getTagCompound());
        return true;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            Block block = world.getBlockState(blockInWorld.pos).getBlock();
            return block.onBlockActivated(world, blockInWorld.pos,
                world.getBlockState(blockInWorld.pos), player, EnumHand.MAIN_HAND, stack,
                blockInWorld.facing, 0.5f, 0.5f, 0.5f);
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
