package notjoe.pointersmod.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import notjoe.pointersmod.api.helpers.NbtHelper;

import java.util.List;

/**
 * Represents an item which has an action referencing ("pointing to") a block in a certain world.
 */
abstract public class PointerAction {
    /**
     * Sets the target of this pointer.
     *
     * @param stack        The pointer to bind.
     * @param blockInWorld Information about the block to bind to.
     * @param world        The world the block was bound in.
     * @return Whether or not the pointer was bound.
     */
    abstract public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld,
        World world);

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
     * Gets the target BlockInWorld of this pointer.
     * *May cause a NullPointerException if hasTarget has not been checked*
     *
     * @param stack The pointer stack.
     * @return A BlockInWorld containing information about the pointer's target.
     */
    public BlockInWorld getPointerTarget(ItemStack stack) {
        return new BlockInWorld(stack.getTagCompound());
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
        BlockInWorld target = getPointerTarget(stack);
        return world.isBlockLoaded(target.pos) &&
            player.canPlayerEdit(target.pos, target.facing, stack) &&
            player.capabilities.allowEdit;
    }
}
