package notjoe.pointersmod.common.item;

import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.api.implementation.BaseTeslaContainerProvider;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
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
        setMaxStackSize(1);
        setMaxDamage((int) pointerAction.getTeslaCapacity());
    }

    @Override public boolean showDurabilityBar(ItemStack stack) {
        if (stack.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) {
            ITeslaHolder teslaHolder =
                stack.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null);
            if(teslaHolder.getStoredPower() == 0) return false;
        }
        return true;
    }

    @Override public int getDamage(ItemStack stack) {
        if (stack.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) {
            ITeslaHolder teslaHolder =
                stack.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null);
            return getMaxDamage() - (int) teslaHolder.getStoredPower();
        }

        return getMaxDamage();
    }

    @Override public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new BaseTeslaContainerProvider(
            new BaseTeslaContainer(pointerAction.getTeslaCapacity(), 10000, 10000));
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn,
        BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        boolean success = false;
        boolean playerIsCreative = playerIn.capabilities.isCreativeMode;
        if (stack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null)) {
            ITeslaProducer producer =
                stack.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null);
            if (producer.takePower(pointerAction.getTeslaPerUse(), true) >= pointerAction
                .getTeslaPerUse() || playerIsCreative) {
                if (playerIn.isSneaking() && hand == EnumHand.OFF_HAND) {
                    success = pointerAction
                        .setPointerTarget(stack, new BlockInWorld(pos, playerIn.dimension, facing),
                            worldIn);
                } else if (playerIn.isSneaking() && hand == EnumHand.MAIN_HAND) {
                    success = pointerAction.pointerActivatedSecondary(stack, worldIn, playerIn);
                } else if (hand == EnumHand.MAIN_HAND) {
                    success = pointerAction.pointerActivated(stack, worldIn, playerIn);
                }
            }

            if (success && !playerIsCreative)
                producer.takePower(pointerAction.getTeslaPerUse(), false);
        }
        return success ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }

    @Override public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn,
        EntityPlayer playerIn, EnumHand hand) {
        boolean success = false;
        boolean playerIsCreative = playerIn.capabilities.isCreativeMode;
        if (stack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null)) {
            ITeslaProducer producer =
                stack.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null);
            if (producer.takePower(pointerAction.getTeslaPerUse(), true) >= pointerAction
                .getTeslaPerUse() || playerIsCreative) {
                if (playerIn.isSneaking() && hand == EnumHand.MAIN_HAND) {
                    success = pointerAction.pointerActivatedSecondary(stack, worldIn, playerIn);
                } else if (hand == EnumHand.MAIN_HAND) {
                    success = pointerAction.pointerActivated(stack, worldIn, playerIn);
                }

                if (success && !playerIsCreative)
                    producer.takePower(pointerAction.getTeslaPerUse(), false);
            }
        }

        if (success) {
            return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
        } else {
            return ActionResult.newResult(EnumActionResult.FAIL, stack);
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

        if (stack.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) {
            ITeslaHolder holder = stack.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null);
            tooltip
                .add(I18n.format("pointers.power", holder.getStoredPower(), holder.getCapacity()));
            tooltip.add(I18n.format("pointers.powerperuse", pointerAction.getTeslaPerUse()));
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
