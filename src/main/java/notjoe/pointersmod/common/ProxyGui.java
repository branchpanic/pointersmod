package notjoe.pointersmod.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import notjoe.pointersmod.api.actions.PointerActionInventory;
import notjoe.pointersmod.client.gui.GuiInvPointer;
import notjoe.pointersmod.common.container.ContainerInvPointer;
import notjoe.pointersmod.common.item.ItemPointerBase;
import notjoe.pointersmod.common.item.ModItems;

public class ProxyGui implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y,
        int z) {
        ItemStack pointerStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (pointerStack.getItem() == ModItems.pointer_inv) {
                    return new GuiInvPointer(new ContainerInvPointer(
                            PointerActionInventory.getStackHandlerFromInvPointer(pointerStack, world, player),
                        player.inventory));
        }
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y,
        int z) {
        ItemStack pointerStack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (pointerStack.getItem() == ModItems.pointer_inv) {
                    return new ContainerInvPointer(
                            PointerActionInventory.getStackHandlerFromInvPointer(pointerStack, world, player), player.inventory);
        }

        return null;
    }
}
