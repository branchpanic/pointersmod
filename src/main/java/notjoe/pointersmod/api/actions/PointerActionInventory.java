package notjoe.pointersmod.api.actions;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;

import java.util.Collections;
import java.util.List;

/**
 * Progress:
 * TODO: Game will crash if the target is destroyed or unloaded (isTargetAccessible is not implemented)
 */
public class PointerActionInventory extends PointerAction {
    private boolean ignoreFacing;

    public PointerActionInventory(boolean ignoreFacing) {
        this.ignoreFacing = ignoreFacing;
    }

    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if (blockInWorld.isTileEntity(world)) {
            if (blockInWorld.getTileEntity(world)
                .hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    ignoreFacing ? null : blockInWorld.facing)) {
                blockInWorld.serializeNbt(stack.getTagCompound());
                String localizedTargetName = world.getBlockState(blockInWorld.pos).getBlock().getLocalizedName();
                stack.getTagCompound().setString("target_name", localizedTargetName);
                return true;
            }
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player) && !world.isRemote) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            IItemHandler handler = blockInWorld.getTileEntity(world)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    ignoreFacing ? null : blockInWorld.facing);
            if (handler != null) {
                player.openGui(PointersMod.INSTANCE, 0, world, 0, 0, 0);
            }

            return true;
        }
        return false;
    }

    @Override public List<String> getExtraInfo(ItemStack stack) {
        NbtHelper.initNbtTagForStack(stack);
        if(stack.getTagCompound().hasKey("target_name"))
            return Collections.singletonList(I18n.format("pointers.targetname", stack.getTagCompound().getString("target_name")));
        return null;
    }

    public IItemHandler getStackHandler(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            IItemHandler handler = blockInWorld.getTileEntity(world)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    ignoreFacing ? null : blockInWorld.facing);
            return handler;
        }

        return null;
    }

    @Override public long getTeslaCapacity() {
        return 100000;
    }

    @Override public long getTeslaPerUse() {
        return 1000;
    }
}
