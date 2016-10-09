package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;

import java.util.Random;

public class PointerActionTeleport extends PointerAction {
    private static Random random = new Random();
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        BlockInWorld blockUp =
            new BlockInWorld(blockInWorld.pos.offset(blockInWorld.facing), blockInWorld.dimension,
                blockInWorld.facing);
        blockUp.serializeNbt(stack.getTagCompound());
        return true;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = new BlockInWorld(stack.getTagCompound());
            player.setPosition(blockInWorld.pos.getX() + 0.5, blockInWorld.pos.getY() + 0.5,
                blockInWorld.pos.getZ() + 0.5);
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
