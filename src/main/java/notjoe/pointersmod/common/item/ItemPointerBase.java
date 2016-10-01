package notjoe.pointersmod.common.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import notjoe.pointersmod.api.BlockInWorld;
import notjoe.pointersmod.api.PointerAction;
import notjoe.pointersmod.api.actions.PointerActionInventory;

import java.util.List;

public class ItemPointerBase extends ModItem {
    private PointerAction pointerAction;

    public ItemPointerBase(String unlocalizedName, PointerAction pointerAction) {
        super(unlocalizedName);
        this.pointerAction = pointerAction;
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn,
        BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean success = false;
        if (playerIn.isSneaking() && hand == EnumHand.OFF_HAND) {
            success = pointerAction
                .setPointerTarget(stack, new BlockInWorld(pos, playerIn.dimension, facing),
                    worldIn);
        } else if (playerIn.isSneaking() && hand == EnumHand.MAIN_HAND) {
            success = pointerAction.pointerActivatedSecondary(stack, worldIn, playerIn);
        } else if (hand == EnumHand.MAIN_HAND) {
            success = pointerAction.pointerActivated(stack, worldIn, playerIn);
        }

        return success ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    @Override public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn,
        EntityPlayer playerIn, EnumHand hand) {
        boolean success = false;
        if (hand == EnumHand.MAIN_HAND) {
            success = pointerAction.pointerActivated(itemStackIn, worldIn, playerIn);
        }

        if (success) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
        } else {
            return ActionResult.newResult(EnumActionResult.FAIL, itemStackIn);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip,
        boolean advanced) {
        if (pointerAction.hasTarget(stack)) {
            BlockInWorld blockInWorld = pointerAction.getPointerTarget(stack);
            tooltip.add(
                I18n.format("pointers.blockpos", blockInWorld.pos.getX(), blockInWorld.pos.getY(),
                    blockInWorld.pos.getZ()));
            tooltip.add(I18n.format("pointers.facing", blockInWorld.facing.getName()));
            tooltip.add(I18n.format("pointers.dimension", blockInWorld.dimension));
            if (pointerAction.getExtraInfo(stack) != null)
                tooltip.addAll(pointerAction.getExtraInfo(stack));
        } else {
            tooltip.add(I18n.format("pointers.notarget"));
        }
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    public IItemHandler getStackHandlerFromPointer(ItemStack stack, World world,
        EntityPlayer player) {
        if (pointerAction instanceof PointerActionInventory) {
            return ((PointerActionInventory) pointerAction).getStackHandler(stack, world, player);
        }
        return null;
    }
}
