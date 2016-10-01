package notjoe.pointersmod.common.container;

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

    public ContainerInvPointer(IItemHandler itemHandler, IInventory playerInventory) {
        this.handler = itemHandler;
        addHandlerSlots(itemHandler);
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 9 + col * 18;
                int y = row * 18 + 70;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        for (int row = 0; row < 9; ++row) {
            int x = 9 + row * 18;
            int y = 58 + 70;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addHandlerSlots(IItemHandler handler) {
        System.out.println(handler.getSlots());
        int slots = handler.getSlots();
        int slot = 0;
        int rows = slots/9 + (slots % 9 > 0? 1: 0);
        int cols = 9;
        for(int row = 0; row < rows; ++row) {
            for(int col = 0; col < cols; ++col) {
                if(slot < slots) {
                    int x = 9 + col * 18;
                    int y = row * 18;
                    addSlotToContainer(new SlotItemHandler(handler, slot, x, y));
                    slot++;
                }
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
                if (!this.mergeItemStack(itemstack1, handler.getSlots(), this.inventorySlots.size(), true)) {
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
