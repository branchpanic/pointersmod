package notjoe.pointersmod.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.RegisterableModel;

/**
 * An extension of Minecraft's Block to serve as a foundation for blocks within this mod.
 * Does what ModItem does, but registers an ItemBlock as well.
 * @see notjoe.pointersmod.common.item.ModItem
 */
public class ModBlock extends Block implements RegisterableModel {
    public ModBlock(String unlocalizedName, Material material) {
        super(material);
        setUnlocalizedName(PointersMod.MODID + "." + unlocalizedName);
        setRegistryName(PointersMod.MODID, unlocalizedName);
        setCreativeTab(PointersMod.tabPointers);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    @Override public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
            new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
