package notjoe.pointersmod.common;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.event.ModEvents;
import notjoe.pointersmod.common.item.ModItems;
import notjoe.pointersmod.common.recipe.ModCraftingRecipes;
import notjoe.pointersmod.common.updater.UpdateChecker;

import java.io.File;

public class ProxyCommon {
    public void preInit(FMLPreInitializationEvent event) {
        PointersMod.logger = event.getModLog();
        PointersMod.config = new Configuration(
            new File(event.getModConfigurationDirectory().getPath(), "pointersmod.cfg"));
        Config.initConfigValues();
        ModItems.preInit();
        ModBlocks.preInit();
        ModCraftingRecipes.addRecipes();
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        NetworkRegistry.INSTANCE.registerGuiHandler(PointersMod.INSTANCE, new ProxyGui());
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (Config.enableUpdateCheck) {
            UpdateChecker ch = new UpdateChecker(Config.updateChannel);
            PointersMod.updateStatus = ch.checkForUpdates();
            PointersMod.updateStatus.getUpdateMessages()
                .forEach(msg -> PointersMod.logger.info(msg));
        } else {
            PointersMod.logger.info("Update checking is disabled.");
        }
    }
}
