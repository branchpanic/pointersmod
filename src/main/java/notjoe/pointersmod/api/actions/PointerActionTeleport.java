package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;

import java.util.List;

public class PointerActionTeleport extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        BlockInWorld blockUp = new BlockInWorld(blockInWorld.pos.offset(blockInWorld.facing), blockInWorld.dimension, blockInWorld.facing);
        blockUp.serializeNbt(stack.getTagCompound());
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = new BlockInWorld(stack.getTagCompound());
            player.setPosition(blockInWorld.pos.getX() + 0.5,
                blockInWorld.pos.getY(),
                blockInWorld.pos.getZ() + 0.5);
            if(player.dimension != blockInWorld.dimension) player.changeDimension(blockInWorld.dimension);
            return true;
        }
        return false;
    }
}
