package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import notjoe.pointersmod.api.BlockDetail;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;

import java.util.Random;

/**
 * A pointer action that teleports the player.
 */
public class PointerActionTeleport extends PointerAction {
    private static Random random = new Random();
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockDetail blockDetail, World world) {
        NbtHelper.initNbtTagForStack(stack);
        BlockDetail blockUp =
            new BlockDetail(blockDetail.pos.offset(blockDetail.facing), blockDetail.dimension,
                blockDetail.facing);
        blockUp.serializeNbt(stack.getTagCompound());
        return true;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockDetail blockDetail = new BlockDetail(stack.getTagCompound());
            player.setPosition(blockDetail.pos.getX() + 0.5, blockDetail.pos.getY() + 0.5,
                blockDetail.pos.getZ() + 0.5);
            for(int i = 0; i < 20; i++) {
                world.spawnParticle(EnumParticleTypes.REDSTONE,
                    player.posX + random.nextGaussian(),
                    player.posY,
                    player.posZ + random.nextGaussian(),
                    random.nextGaussian(),
                    random.nextGaussian(),
                    random.nextGaussian());
            }
            return true;
        }
        return false;
    }

    @Override public long getTeslaPerUse() {
        return Config.teleportTpu;
    }
}
