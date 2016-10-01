package notjoe.pointersmod.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import notjoe.pointersmod.common.block.ModBlocks;

public class TileReceiverRedstone extends TileEntity {
    private boolean isPowered = false;

    @Override public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = super.serializeNBT();
        compound.setBoolean("is_powered", isPowered);
        return compound;
    }

    @Override public void deserializeNBT(NBTTagCompound nbt) {
        super.deserializeNBT(nbt);
        isPowered = nbt.getBoolean("is_powered");
    }

    public boolean getIsPowered() {
        return isPowered;
    }

    public void togglePower() {
        isPowered = !isPowered;
        getWorld().notifyBlockOfStateChange(getPos(), ModBlocks.receiver_redstone);
    }
}
