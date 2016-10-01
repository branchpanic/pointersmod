package notjoe.pointersmod.common.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import notjoe.pointersmod.common.block.ModBlocks;

import javax.annotation.Nullable;

public class TileReceiverRedstone extends TileEntity {
    private boolean isPowered;

    @Override public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("is_powered", getIsPowered());
        return compound;
    }

    @Override public void readFromNBT(NBTTagCompound compound) {
        System.out.println(worldObj.isRemote);
        setPowered(compound.getBoolean("is_powered"));
    }

    public void togglePowered() {
        setPowered(!getIsPowered());
    }

    public boolean getIsPowered() {
        worldObj.notifyBlockOfStateChange(pos, ModBlocks.receiver_redstone);
        markDirty();
        return isPowered;
    }

    public void setPowered(boolean powered) {
        isPowered = powered;
        worldObj.notifyBlockOfStateChange(pos, ModBlocks.receiver_redstone);
        markDirty();
    }
}
