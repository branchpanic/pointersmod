package notjoe.pointersmod.api;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * TODO: There's probably a class that does this already.
 * Contains specific information about a block or single face on it: Position, world, and facing
 */
public class BlockInWorld {
    public final BlockPos pos;
    public final int dimension;
    public final EnumFacing facing;

    public BlockInWorld(BlockPos pos, int dimension, EnumFacing facing) {
        this.pos = pos;
        this.dimension = dimension;
        this.facing = facing;
    }

    public BlockInWorld(NBTTagCompound fromCompound) {
        this.pos = new BlockPos(fromCompound.getInteger("block_pos_x"),
            fromCompound.getInteger("block_pos_y"),
            fromCompound.getInteger("block_pos_z"));
        this.dimension = fromCompound.getInteger("dimension");
        this.facing = EnumFacing.byName(fromCompound.getString("facing"));
    }

    public boolean isTileEntity(World world) {
        return world.getTileEntity(pos) != null;
    }

    public TileEntity getTileEntity(World world) {
        return world.getTileEntity(pos);
    }

    public void serializeNbt(NBTTagCompound toCompound) {
        toCompound.setInteger("block_pos_x", pos.getX());
        toCompound.setInteger("block_pos_y", pos.getY());
        toCompound.setInteger("block_pos_z", pos.getZ());
        toCompound.setInteger("dimension", dimension);
        toCompound.setString("facing", facing.getName());
    }
}
