package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.common.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PointerActionFluid extends PointerAction {
    @Override
    public boolean setPointerTarget(ItemStack stack, BlockInWorld blockInWorld, World world) {
        if (blockInWorld.isTileEntity(world) && blockInWorld.getTileEntity(world)
            .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, blockInWorld.facing)) {
            blockInWorld.serializeNbt(stack.getTagCompound());
            return true;
        }

        return false;
    }

    @Override public boolean pointerActivated(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld target = new BlockInWorld(stack.getTagCompound());
            if (target.isTileEntity(world) && target.getTileEntity(world)
                .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing)) {
                IFluidHandler targetFluidHandler = target.getTileEntity(world)
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing);
                FluidStack transferStack =
                    targetFluidHandler.drain(Config.fluidPointerTransfer, false);
                if (transferStack != null && transferStack.amount == Config.fluidPointerTransfer) {
                    List<ItemStack> fluidCapableStacks = getFluidCapableStacks(player);
                    for (ItemStack itemStack : fluidCapableStacks) {
                        IFluidHandler stackFluidHandler = itemStack
                            .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (stackFluidHandler.fill(transferStack, false)
                            == Config.fluidPointerTransfer) {
                            targetFluidHandler.drain(transferStack, true);
                            stackFluidHandler.fill(transferStack, true);
                            world.notifyNeighborsOfStateChange(target.pos,
                                world.getBlockState(target.pos).getBlock());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean pointerActivatedSecondary(ItemStack stack, World world, EntityPlayer player) {
        if (hasTarget(stack) && isTargetAccessible(stack, world, player)) {
            BlockInWorld target = new BlockInWorld(stack.getTagCompound());
            if (target.isTileEntity(world) && target.getTileEntity(world)
                .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing)) {
                IFluidHandler targetFluidHandler = target.getTileEntity(world)
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing);
                List<ItemStack> fluidCapableStacks = getFluidCapableStacks(player);
                for (ItemStack itemStack : fluidCapableStacks) {
                    IFluidHandler handler = itemStack
                        .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                    FluidStack transferStack = handler.drain(Config.fluidPointerTransfer, false);
                    if (transferStack != null
                        && transferStack.amount == Config.fluidPointerTransfer) {
                        if (targetFluidHandler.fill(transferStack, false)
                            == Config.fluidPointerTransfer) {
                            handler.drain(transferStack, true);
                            targetFluidHandler.fill(transferStack, true);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override public long getTeslaPerUse() {
        return Config.fluidTpu;
    }

    private List<ItemStack> getFluidCapableStacks(EntityPlayer player) {
        ArrayList<ItemStack> capableStacks = new ArrayList<>();
        ArrayList<ItemStack> allPlayerInventories = new ArrayList<>();
        Collections.addAll(allPlayerInventories, player.inventory.armorInventory);
        Collections.addAll(allPlayerInventories, player.inventory.mainInventory);
        Collections.addAll(allPlayerInventories, player.inventory.offHandInventory);
        Collections.addAll(capableStacks, allPlayerInventories.stream().filter(
            stack -> stack != null && stack
                .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
            .toArray(ItemStack[]::new));
        return capableStacks;
    }
}
