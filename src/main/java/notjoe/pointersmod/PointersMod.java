package notjoe.pointersmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import notjoe.pointersmod.common.ProxyCommon;
import notjoe.pointersmod.common.item.ModItems;
import notjoe.pointersmod.common.updater.UpdateStatus;
import org.apache.logging.log4j.Logger;

/**
 * Main mod class.
 */
@Mod(modid = PointersMod.MODID, version = PointersMod.VERSION, name = PointersMod.MODNAME,
    dependencies = PointersMod.DEPENDENCIES) public class PointersMod {
    /**
     * Mod metadata: ID, human readable name, version, dependencies
     */
    public static final String MODID = "pointersmod";
    public static final String MODNAME = "Pointers";
    public static final String VERSION = "1.10.2-0.2-beta.0";
    public static final String DEPENDENCIES = "required-after:tesla@[1.2.1.49,);";

    /**
     * Networking & Proxy values
     */
    @Mod.Instance(value = PointersMod.MODID) public static PointersMod INSTANCE;
    @SidedProxy(clientSide = "notjoe.pointersmod.client.ProxyClient",
        serverSide = "notjoe.pointersmod.common.ProxyCommon") public static ProxyCommon proxy;

    /**
     * Logger & Config
     */
    public static Logger logger;
    public static Configuration config;

    /**
     * Update status
     */
    public static UpdateStatus updateStatus = UpdateStatus.failedUpdate();

    /**
     * Creative Tab
     */
    public static CreativeTabs tabPointers = new CreativeTabs(PointersMod.MODID) {
        @Override public Item getTabIconItem() {
            return ModItems.pointer_teleport;
        }
    };

    /**
     * Called during Forge's pre-init event.
     *
     * @param event Event data.
     * @see ProxyCommon
     */
    @Mod.EventHandler public static void onPreInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    /**
     * Called during Forge's init event.
     *
     * @param event Event data.
     * @see ProxyCommon
     */
    @Mod.EventHandler public static void onInit(FMLInitializationEvent event) {
        proxy.init(event);
    }

    /**
     * Called during Forge's post-init event.
     *
     * @param event Event data.
     * @see ProxyCommon
     */
    @Mod.EventHandler public static void onPostInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
