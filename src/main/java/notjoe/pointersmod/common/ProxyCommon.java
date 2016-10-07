package notjoe.pointersmod.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.item.ModItems;
import notjoe.pointersmod.common.recipes.ModCraftingRecipes;

import java.io.File;

public class ProxyCommon {
    public void preInit(FMLPreInitializationEvent event) {
        PointersMod.config = new Configuration(
            new File(event.getModConfigurationDirectory().getPath(), "pointersmod.cfg"));
        Config.initConfigValues();

        ModItems.preInit();
        ModBlocks.preInit();

        ModCraftingRecipes.addRecipes();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(PointersMod.INSTANCE, new ProxyGui());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
