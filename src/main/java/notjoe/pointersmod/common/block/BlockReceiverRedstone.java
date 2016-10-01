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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import notjoe.pointersmod.common.tile.TileReceiverRedstone;

import java.util.Random;

public class BlockReceiverRedstone extends ModBlock implements ITileEntityProvider {
    public static final AxisAlignedBB BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
    public BlockReceiverRedstone() {
        super("receiver_redstone", Material.CIRCUITS);
        isBlockContainer = true;

        GameRegistry.registerTileEntity(TileReceiverRedstone.class, "te_receiver_redstone");
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
        if(blockAccess.getTileEntity(pos) instanceof TileReceiverRedstone) {
            return ((TileReceiverRedstone) blockAccess.getTileEntity(pos)).getIsPowered()? 15: 0;
        }

        return 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockReceiverRedstone.BLOCK_AABB;
    }

    @Override public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT) @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if(worldIn.isRemote) {
            if(worldIn.getTileEntity(pos) instanceof TileReceiverRedstone && ((TileReceiverRedstone) worldIn.getTileEntity(pos)).getIsPowered()) {
                System.out.println("Spawning particle");
                worldIn.spawnParticle(EnumParticleTypes.REDSTONE,
                    0.5 + 0.2 * rand.nextGaussian(), 1.0 + 0.2 * rand.nextGaussian(), 0.5 + 0.2 * rand.nextGaussian(), 0, 0, 0, new int[0]);
            }
        }
        System.out.println("Random display tick");
    }
}
