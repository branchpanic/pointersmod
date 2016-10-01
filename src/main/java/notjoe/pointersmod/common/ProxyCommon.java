package notjoe.pointersmod.common;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.block.ModBlocks;
import notjoe.pointersmod.common.item.ModItems;

public class ProxyCommon {
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.preInit();
        ModBlocks.preInit();
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(PointersMod.INSTANCE, new ProxyGui());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
