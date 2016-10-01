package notjoe.pointersmod.client;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.api.RegisterableModel;
import notjoe.pointersmod.common.ProxyCommon;

public class ProxyClient extends ProxyCommon {
    @Override public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(PointersMod.MODID);
        for (Object item : Item.REGISTRY) {
            if (item instanceof RegisterableModel) {
                ((RegisterableModel) item).registerModel();
            }
        }

        for (Object block : Block.REGISTRY) {
            if (block instanceof RegisterableModel) {
                ((RegisterableModel) block).registerModel();
            }
        }
    }

    @Override public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
