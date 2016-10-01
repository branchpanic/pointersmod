package notjoe.pointersmod.common.container;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;

public class ContainerInvPointer extends Container {
    private IItemHandler handler;
    public int width;
    public int height;

    public ContainerInvPointer(IItemHandler itemHandler, IInventory playerInventory) {
        this.handler = itemHandler;
        addSlots(itemHandler, playerInventory);
    }

    private void addSlots(IItemHandler handler, IInventory playerInventory) {
        int columns = 9;
        int handlerRows = handler.getSlots() / 9 + (handler.getSlots() % 9 > 0 ? 1 : 0);
        int playerRows = 3;
        int hotbarRows = 1;

        int slotDimension = 18;

        int spacing = 9;

        int handlerHeight = slotDimension * handlerRows + spacing;
        int inventoryHeight = handlerHeight + slotDimension * playerRows + spacing;
        int totalHeight = inventoryHeight + slotDimension * hotbarRows;

        width = columns * slotDimension;
        height = totalHeight;

        int slot = 0;

        for(int row = 0; row < handlerRows; ++row) {
            for(int col = 0; col < columns; ++col) {
                int x = spacing + col * slotDimension;
                int y = row * slotDimension;
                if(slot < handler.getSlots())
                    this.addSlotToContainer(new SlotItemHandler(handler, slot, x, y));
                slot++;
            }
        }

        for(int row = 0; row < playerRows; ++row) {
            for(int col = 0; col < columns; ++col) {
                int x = spacing + col * slotDimension;
                int y = row * slotDimension + handlerHeight;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for(int row = 0; row < hotbarRows; ++row) {
            for(int col = 0; col < columns; ++col) {
                int x = spacing + col * slotDimension;
                int y = row * slotDimension + inventoryHeight;
                this.addSlotToContainer(new Slot(playerInventory, col, x, y));
            }
        }
    }

    @Nullable @Override public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < handler.getSlots()) {
                if (!this.mergeItemStack(itemstack1, handler.getSlots(), this.inventorySlots.size(),
                    true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, handler.getSlots(), false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
