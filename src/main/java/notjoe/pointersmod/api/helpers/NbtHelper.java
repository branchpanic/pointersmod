package notjoe.pointersmod.api.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NbtHelper {
    public static void initNbtTagForStack(ItemStack stack) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());
    }

    public static boolean stackHasBlockData(ItemStack stack) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            return compound.hasKey("block_pos_x") &&
                compound.hasKey("block_pos_y") &&
                compound.hasKey("block_pos_z") &&
                compound.hasKey("dimension") &&
                compound.hasKey("facing");
        }

        return false;
    }
}
