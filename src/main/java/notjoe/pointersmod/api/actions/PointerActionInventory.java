package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.helpers.NbtHelper;

import java.util.List;

/**
 * Progress:
 *  TODO: Game will crash if the target is destroyed or unloaded (isTargetAccessible is not implemented)
 */
public class PointerActionInventory implements PointerAction {
    private boolean ignoreFacing;

    public PointerActionInventory(boolean ignoreFacing) {
        this.ignoreFacing = ignoreFacing;
    }

    @Override public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        NbtHelper.initNbtTagForStack(stack);
        if(blockInWorld.isTileEntity(world)) {
            if(blockInWorld.getTileEntity(world).hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ignoreFacing? null: blockInWorld.facing)) {
                blockInWorld.serializeNbt(stack.getTagCompound());
                return true;
            }
        }
        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player) && !world.isRemote) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            IItemHandler handler = blockInWorld.getTileEntity(world).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ignoreFacing? null: blockInWorld.facing);
            if(handler != null) {
                /*for(int i = 0; i < handler.getSlots(); i++) {
                    player.addChatComponentMessage(new TextComponentString(i + " " + (handler.getStackInSlot(i) != null? handler.getStackInSlot(i).getDisplayName(): "Nothing")));
                }*/
                player.openGui(PointersMod.INSTANCE, 0, world, 0, 0, 0);
            }

            return true;
        }
        return false;
    }

    @Override public List<String> getExtraInfo(ItemStack stack) {
        return null;
    }

    @Override public BlockInWorld getPointerTarget(ItemStack stack) {
        if(hasTarget(stack)) {
            return new BlockInWorld(stack.getTagCompound());
        }
        return null;
    }

    @Override public boolean hasTarget(ItemStack stack) {
        NbtHelper.initNbtTagForStack(stack);
        return NbtHelper.stackHasBlockData(stack);
    }

    @Override public boolean isTargetAccessible(ItemStack stack, World world, EntityPlayer player) {
        BlockInWorld target = new BlockInWorld(stack.getTagCompound());
        return world.isBlockLoaded(target.pos) &&
            player.canPlayerEdit(target.pos, target.facing, stack) &&
            player.capabilities.allowEdit;
    }

    public IItemHandler getStackHandler(ItemStack stack, World world, EntityPlayer player) {
        if(hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld blockInWorld = getPointerTarget(stack);
            IItemHandler handler = blockInWorld.getTileEntity(world)
                .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, ignoreFacing? null: blockInWorld.facing);
            return handler;
        }

        return null;
    }
}
