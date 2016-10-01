package notjoe.pointersmod.api;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PointerHandler {
    public static final String NBT_IS_BOUND = "is_bound";
    public static final String NBT_BLOCK_NAME = "block_name";
    public static final String NBT_IS_TE = "is_tile_entity";
    public static final String NBT_TARGET_X = "target_x";
    public static final String NBT_TARGET_Y = "target_y";
    public static final String NBT_TARGET_Z = "target_z";

    private static NBTTagCompound getTagCompoundSafely(ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        return stack.getTagCompound();
    }

    @Nullable public static BlockPos getBoundPos(ItemStack stack) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        if(compound.getBoolean(PointerHandler.NBT_IS_BOUND)) {
            return new BlockPos(
                compound.getInteger(PointerHandler.NBT_TARGET_X),
                compound.getInteger(PointerHandler.NBT_TARGET_Y),
                compound.getInteger(PointerHandler.NBT_TARGET_Z)
            );
        }

        return null;
    }

    public static boolean isBound(ItemStack stack) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        return compound.getBoolean(PointerHandler.NBT_IS_BOUND);
    }

    public static boolean targetIsLoaded(ItemStack stack, World world) {
        return world.isBlockLoaded(PointerHandler.getBoundPos(stack));
    }

    public static void update(ItemStack stack, World world) {
        if(targetIsLoaded(stack, world)) {
            bindToPos(stack, getBoundPos(stack), world);
        }
    }

    public static String getBlockName(ItemStack stack) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        return compound.getString(PointerHandler.NBT_BLOCK_NAME);
    }

    public static boolean getIsTe(ItemStack stack) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        return compound.getBoolean(PointerHandler.NBT_IS_TE);
    }

    public static void bindToPos(ItemStack stack, BlockPos pos, World world) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        compound.setBoolean(PointerHandler.NBT_IS_BOUND, true);
        compound.setInteger(PointerHandler.NBT_TARGET_X, pos.getX());
        compound.setInteger(PointerHandler.NBT_TARGET_Y, pos.getY());
        compound.setInteger(PointerHandler.NBT_TARGET_Z, pos.getZ());
        compound.setString(PointerHandler.NBT_BLOCK_NAME, world.getBlockState(pos).getBlock().getUnlocalizedName());
        compound.setBoolean(PointerHandler.NBT_IS_TE, world.getBlockState(pos).getBlock() instanceof ITileEntityProvider);
    }

    public static void unbind(ItemStack stack) {
        NBTTagCompound compound = getTagCompoundSafely(stack);
        compound.setBoolean(PointerHandler.NBT_IS_BOUND, false);
        compound.setInteger(PointerHandler.NBT_TARGET_X, 0);
        compound.setInteger(PointerHandler.NBT_TARGET_Y, 0);
        compound.setInteger(PointerHandler.NBT_TARGET_Z, 0);
        compound.setString(PointerHandler.NBT_BLOCK_NAME, "");
        compound.setBoolean(PointerHandler.NBT_IS_TE, false);
    }

    public static boolean clickBlock(ItemStack pointerStack, @Nullable ItemStack usingStack, World worldIn, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(getBoundPos(pointerStack) == null) return false;
        BlockPos boundPos = getBoundPos(pointerStack);
        worldIn.getBlockState(boundPos).getBlock().onBlockActivated(
            worldIn, boundPos, worldIn.getBlockState(boundPos), playerIn, hand, usingStack, facing, hitX, hitY, hitZ
        );

        return true;
    }
}
