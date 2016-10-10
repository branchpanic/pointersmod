package notjoe.pointersmod.api.actions;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockDetail;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;

/**
 * A pointer action that calls onBlockActivated for a block.
 */
public class PointerActionWorld extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockDetail blockDetail, World world) {
        NbtHelper.initNbtTagForStack(stack);
        blockDetail.serializeNbt(stack.getTagCompound());
        return true;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockDetail blockDetail = getPointerTarget(stack);
            Block block = world.getBlockState(blockDetail.pos).getBlock();
            return block
                .onBlockActivated(world, blockDetail.pos, world.getBlockState(blockDetail.pos),
                    player, EnumHand.MAIN_HAND, stack, blockDetail.facing, 0.5f, 0.5f, 0.5f);
        }
        return false;
    }

    @Override public long getTeslaPerUse() {
        return Config.worldTpu;
    }
}
