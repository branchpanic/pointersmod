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
            if(PointersMod.updateStatus.isLatestVersion) {
                PointersMod.logger.info("You're running the latest " + PointersMod.updateStatus.releaseChannel.toUpperCase() + " version of Pointers!");
            } else {
                if(PointersMod.updateStatus.getLatestRelease().isEmpty()) {
                    PointersMod.logger.warn("No Pointers version of type " + PointersMod.updateStatus.releaseChannel.toUpperCase() + " was found!");
                    PointersMod.logger.warn("Latest ALPHA:\t" + PointersMod.updateStatus.latestAlpha);
                    PointersMod.logger.warn("Latest BETA:\t" + PointersMod.updateStatus.latestBeta);
                    PointersMod.logger.warn("Latest RELEASE:\t" + PointersMod.updateStatus.latestRelease);
                } else {
                    PointersMod.logger.info("*** A new " + PointersMod.updateStatus.releaseChannel.toUpperCase() + " version of Pointers is available! " + PointersMod.updateStatus.getLatestRelease() + " ***");
                }
            }
        } else {
            PointersMod.logger.info("Update checking is disabled.");
        }
    }
}
