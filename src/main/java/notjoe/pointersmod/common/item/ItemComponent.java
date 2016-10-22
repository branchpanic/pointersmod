package notjoe.pointersmod.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * A crafting component item. Has no functionality and makes use of sub-items.
 */
public class ItemComponent extends ModItem {
    public static final String[] TYPES = {"pointer"};

    public ItemComponent() {
        super("component");
        setHasSubtypes(true);
    }

    @Override @Nonnull public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + metaToString(stack.getItemDamage());
    }

    @Override public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < ItemComponent.TYPES.length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override public void registerModel() {
        for (int i = 0; i < ItemComponent.TYPES.length; i++) {
            ModelLoader.setCustomModelResourceLocation(this, i,
                new ModelResourceLocation(getRegistryName() + "_" + metaToString(i), "inventory"));
        }
    }

    private String metaToString(int meta) {
        if (meta < ItemComponent.TYPES.length)
            return ItemComponent.TYPES[meta];
        return ItemComponent.TYPES[0];
    }
}
