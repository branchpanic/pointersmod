package notjoe.pointersmod.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

import java.util.Random;

public class BlockReceiverRedstone extends ModBlock implements ITileEntityProvider {
    public static final AxisAlignedBB BLOCK_AABB =
        new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

    public BlockReceiverRedstone() {
        super("receiver_redstone", Material.CIRCUITS);
        isBlockContainer = true;
    }

    @Override public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileReceiverRedstone();
    }

    @Override public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
        EnumFacing side) {
        return getIsPowered(pos, blockAccess)? 15: 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockReceiverRedstone.BLOCK_AABB;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(getIsPowered(pos, worldIn)) {
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE,
                pos.getX() + 0.5,
                pos.getY() + 0.9,
                pos.getZ() + 0.5,
                0, 0, 0
            );
        }
    }

    @Override public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    private boolean getIsPowered(BlockPos pos, IBlockAccess world) {
        return world.getTileEntity(pos) instanceof TileReceiverRedstone
            && ((TileReceiverRedstone) world.getTileEntity(pos)).getIsPowered();
    }
}
