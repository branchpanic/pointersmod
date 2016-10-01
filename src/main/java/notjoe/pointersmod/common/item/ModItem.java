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

public class ModItem extends Item implements RegisterableModel {
    private EnumRarity rarity = EnumRarity.COMMON;

    public ModItem(String unlocalizedName) {
        setUnlocalizedName(PointersMod.MODID + "." + unlocalizedName);
        setRegistryName(PointersMod.MODID, unlocalizedName);
        setCreativeTab(PointersMod.tabPointers);
        GameRegistry.register(this);
    }

    public void setRarity(EnumRarity rarity) {
        this.rarity = rarity;
    }

    @Override public EnumRarity getRarity(ItemStack stack) {
        return rarity;
    }

    @Override @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip,
        boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override @SideOnly(Side.CLIENT) public void registerModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
            new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack);
    }
}
