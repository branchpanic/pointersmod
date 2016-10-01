package notjoe.pointersmod.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Represents an item which has an action referencing ("pointing to") a block in a certain world.
 */
public interface PointerAction {
    /**
     * Sets the target of this pointer.
     * @param stack The pointer to bind.
     * @param blockInWorld Information about the block to bind to.
     * @param world The world the block was bound in.
     * @return Whether or not the pointer was bound.
     */
    boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world);

    /**
     * Called when this pointer is activated by right clicking.
     * @param stack The pointer to activate.
     * @return Whether or not the action was successful.
     */
    boolean pointerActivated(ItemStack stack, World world, EntityPlayer player);

    /**
     * Get information about this pointer for use in the HUD overlay (if enabled) and item tooltip.
     * Target position and name do not need to be included, as they are added separately.
     * @param stack The pointer to get information about.
     * @return A list of *unlocalized* Strings containing information about this pointer.
     */
    List<String> getExtraInfo(ItemStack stack);

    BlockInWorld getPointerTarget(ItemStack stack);

    boolean hasTarget(ItemStack stack);

    boolean isTargetAccessible(ItemStack stack, World world, EntityPlayer player);
}
