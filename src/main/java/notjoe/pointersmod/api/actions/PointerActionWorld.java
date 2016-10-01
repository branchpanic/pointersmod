package notjoe.pointersmod.api.actions;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;

public class PointerActionWorld extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        blockInWorld.serializeNbt(stack.getTagCompound());
        return true;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            Block block = world.getBlockState(blockInWorld.pos).getBlock();
            return block
                .onBlockActivated(world, blockInWorld.pos, world.getBlockState(blockInWorld.pos),
                    player, EnumHand.MAIN_HAND, stack, blockInWorld.facing, 0.5f, 0.5f, 0.5f);
        }

        return false;
    }
}
