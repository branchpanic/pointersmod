package notjoe.pointersmod.api.actions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;

import java.util.LinkedList;
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
            if (target.getTileEntity(world)
                .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing)) {
                IFluidHandler handler = target.getTileEntity(world)
                    .getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, target.facing);
                List<ItemStack> fluidStacks = new LinkedList<>();
                for (ItemStack s : player.inventory.mainInventory) {
                    if (s != null && s
                        .hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) {
                        fluidStacks.add(s);
                    }
                }

                if (handler != null && !fluidStacks.isEmpty()) {
                    FluidStack fluidStack = handler.drain(1000, false);
                    for (ItemStack s : fluidStacks) {
                        IFluidHandler stackHandler =
                            s.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (fluidStack.amount == stackHandler.fill(fluidStack, false)) {
                            handler.drain(1000, true);
                            stackHandler.fill(fluidStack, true);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
