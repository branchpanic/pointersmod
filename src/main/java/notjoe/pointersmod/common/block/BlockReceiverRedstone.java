package notjoe.pointersmod.common.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * A block whose redstone output is toggleable with a Redstone Pointer.
 */
public class BlockReceiverRedstone extends ModBlock {
    public static final AxisAlignedBB BLOCK_AABB =
        new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
    public static final PropertyBool POWERED = PropertyBool.create("powered");

    public BlockReceiverRedstone() {
        super("receiver_redstone", Material.CIRCUITS);
        setDefaultState(
            blockState.getBaseState().withProperty(BlockReceiverRedstone.POWERED, false));
    }

    @Override public boolean canProvidePower(IBlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos,
        EnumFacing side) {
        return blockState.getValue(BlockReceiverRedstone.POWERED) ? 15 : 0;
    }

    @Override protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BlockReceiverRedstone.POWERED);
    }

    @Override public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(BlockReceiverRedstone.POWERED, meta != 0);
    }

    @Override public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockReceiverRedstone.POWERED) ? 1 : 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BlockReceiverRedstone.BLOCK_AABB;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(BlockReceiverRedstone.POWERED)) {
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX() + 0.5, pos.getY() + 0.9,
                pos.getZ() + 0.5, 0, 0, 0);
        }
    }

    @Override public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world,
        BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }
}
