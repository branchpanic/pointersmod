package notjoe.pointersmod.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;

import java.util.List;

/**
 * Represents an item which has an action referencing ("pointing to") a block in a certain world.
 */
abstract public class PointerAction {
    /**
     * Sets the target of this pointer.
     *
     * @param stack       The pointer to bind.
     * @param blockDetail Information about the block to bind to.
     * @param world       The world the block was bound in.
     * @return Whether or not the pointer was bound.
     */
    abstract public boolean setPointerTarget(ItemStack stack, BlockDetail blockDetail, World world);

    /**
     * Called when this pointer is activated by right clicking.
     *
     * @param stack  The pointer to activate.
     * @param world  The world of the player.
     * @param player The player who activated the pointer.
     * @return Whether or not the action was successful.
     */
    abstract public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player);

    /**
     * Called when this pointer is activated by crouching and right clicking.
     *
     * @param stack  The pointer to activate.
     * @param world  The world of the player.
     * @param player The player who activated the pointer.
     * @return Whether or not the action was successful.
     */
    public boolean pointerActivatedSecondary(ItemStack stack, World world, EntityPlayer player) {
        return pointerActivated(stack, world, player);
    }

    /**
     * Get information about this pointer for use in the HUD overlay (if enabled) and item tooltip.
     * Target position and name do not need to be included, as they are added separately.
     *
     * @param stack The pointer to get information about.
     * @return A list of Strings containing information about this pointer.
     */
    public List<String> getExtraInfo(ItemStack stack) {
        return null;
    }

    /**
     * Gets the target BlockDetail of this pointer.
     * *May cause a NullPointerException if hasTarget has not been checked*
     *
     * @param stack The pointer stack.
     * @return A BlockDetail containing information about the pointer's target.
     */
    public BlockDetail getPointerTarget(ItemStack stack) {
        return new BlockDetail(stack.getTagCompound());
    }

    /**
     * Gets whether or not the pointer currently has a target.
     *
     * @param stack The pointer stack.
     * @return Whether or not the pointer is bound to a target.
     */
    public boolean hasTarget(ItemStack stack) {
        NbtHelper.initNbtTagForStack(stack);
        return NbtHelper.stackHasBlockData(stack);
    }

    /**
     * Gets whether or not the target of the pointer is accessible (i.e. is loaded, player can modify block)
     *
     * @param stack  The pointer stack.
     * @param world  The world of the player.
     * @param player The player who activated the pointer.
     * @return Whether or not the pointer's target is accessible.
     */
    public boolean isTargetAccessible(ItemStack stack, World world, EntityPlayer player) {
        BlockDetail target = getPointerTarget(stack);
        if (!world.isBlockLoaded(target.pos)) {
            ForgeChunkManager.forceChunk(ForgeChunkManager
                    .requestTicket(PointersMod.INSTANCE, world, ForgeChunkManager.Type.NORMAL),
                new ChunkPos(world.getChunkFromBlockCoords(target.pos).xPosition,
                    world.getChunkFromBlockCoords(target.pos).zPosition));
        }
        return world.isBlockLoaded(target.pos) &&
            player.canPlayerEdit(target.pos, target.facing, stack) &&
            player.capabilities.allowEdit &&
            player.dimension == target.dimension &&
            (Config.pointerOperatingRange <= 0
                || Math.sqrt(player.getDistanceSq(target.pos)) <= Config.pointerOperatingRange);
    }

    /**
     * Gets the amount of energy that this pointer can hold.
     *
     * @return The amount of energy that this pointer can hold.
     */
    public long getTeslaCapacity() {
        return Config.pointerCapacity;
    }

    /**
     * Gets the amount of energy taken per use. The pointer cannot be used if it contains less than this amount of energy.
     *
     * @return The amount of energy taken per use.
     */
    public long getTeslaPerUse() {
        return 1000;
    }
}
