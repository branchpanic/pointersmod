package notjoe.pointersmod.common.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.RegisterableModel;

import java.util.List;

/**
 *  An extension of Minecraft's Item to use as a foundation for items in this mod.
 */
public class ModItem extends Item implements RegisterableModel {
    /**
     * Constructor for a ModItem. Sets an unlocalized name, registry name, and creative tab.
     * In addition, registers the item within the GameRegistry.
     * @param internalName The name of the item used in the unlocalized and registry name.
     */
    ModItem(String internalName) {
        setUnlocalizedName(PointersMod.MODID + "." + internalName);
        setRegistryName(PointersMod.MODID, internalName);
        setCreativeTab(PointersMod.tabPointers);
        GameRegistry.register(this);
    }

    /**
     * Sets this item's model's resource location. Called during ClientProxy.preInit.
     */
    @Override @SideOnly(Side.CLIENT) public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
            new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
