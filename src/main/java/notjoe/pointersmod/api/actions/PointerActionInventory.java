package notjoe.pointersmod.api.actions;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.BlockDetail;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;
import notjoe.pointersmod.common.Config;
import notjoe.pointersmod.common.item.ItemPointerBase;
import notjoe.pointersmod.common.item.ModItems;

import java.util.Collections;
import java.util.List;

/**
 * A pointer action that transfers items.
 */
public class PointerActionInventory extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockDetail blockDetail, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if (blockDetail.isTileEntity(world)) {
            if (blockDetail.getTileEntity(world)
                .hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    blockDetail.facing)) {
                blockDetail.serializeNbt(stack.getTagCompound());
                String localizedTargetName =
                    world.getBlockState(blockDetail.pos).getBlock().getLocalizedName();
                stack.getTagCompound().setString("target_name", localizedTargetName);
                return true;
            }
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player) && !world.isRemote) {
            BlockDetail blockDetail = getPointerTarget(stack);
            IItemHandler handler = blockDetail.getTileEntity(world)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    blockDetail.facing);
            if (handler != null) {
                player.openGui(PointersMod.INSTANCE, 0, world, 0, 0, 0);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean pointerActivatedSecondary(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            IItemHandler targetHandler = getStackHandler(stack, world, player);
            for(int i = 0; i < player.inventory.mainInventory.length; i++) {
                if(player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i] != stack) {
                    player.inventory.mainInventory[i] = moveStackToHandler(player.inventory.mainInventory[i], targetHandler);
                }
            }
            player.inventory.markDirty();

            return true;
        }

        return false;
    }

    @Override public List<String> getExtraInfo(ItemStack stack) {
        NbtHelper.initNbtTagForStack(stack);
        if (stack.getTagCompound().hasKey("target_name"))
            return Collections.singletonList(I18n.format("pointers.targetname",
                stack.getTagCompound().getString("target_name")));
        return null;
    }

    private ItemStack moveStackToHandler(ItemStack stack, IItemHandler handler) {
        // First pass: Try to put stacks with others
        for(int i = 0; i < handler.getSlots(); i++) {
            if(handler.getStackInSlot(i) != null && handler.getStackInSlot(i).isItemEqual(stack)) {
                stack = handler.insertItem(i, stack, false);
            }
        }
        // Second pass: Try to put stack in an empty slot
        if(stack != null) {
            for(int i = 0; i < handler.getSlots(); i++) {
                if(handler.getStackInSlot(i) == null) {
                    stack = handler.insertItem(i, stack, false);
                    break;
                }
            }
        }
        return stack;
    }

    @Override public boolean isTargetAccessible(ItemStack stack, World world, EntityPlayer player) {
        BlockDetail target = new BlockDetail(stack.getTagCompound());
        return super.isTargetAccessible(stack, world, player) &&
            target.isTileEntity(world) && target.getTileEntity(world).hasCapability(
            CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, target.facing);

    }

    public IItemHandler getStackHandler(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockDetail blockDetail = getPointerTarget(stack);
            return blockDetail.getTileEntity(world)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                    blockDetail.facing);
        }

        return null;
    }

    public static IItemHandler getStackHandlerFromInvPointer(ItemStack stack, World world, EntityPlayer player) {
        if(stack.getItem() == ModItems.pointer_inv) {
            // jesus christ
            return ((PointerActionInventory)(((ItemPointerBase) ModItems.pointer_inv).pointerAction)).getStackHandler(stack, world, player);
        }

        return null;
    }

    @Override public long getTeslaPerUse() {
        return Config.inventoryTpu;
    }
}
