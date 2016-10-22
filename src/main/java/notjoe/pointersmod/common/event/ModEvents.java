package notjoe.pointersmod.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.Config;

/**
 * Events within the mod.
 */
public class ModEvents {
    /**
     * Notifies a player through chat if an update is available.
     *
     * @param event Event data.
     */
    @SubscribeEvent public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (Config.enableUpdateCheck && event.getEntity() instanceof EntityPlayer && event
            .getWorld().isRemote && !PointersMod.updateStatus.isLatestVersion) {
            EntityPlayer e = (EntityPlayer) event.getEntity();
            PointersMod.updateStatus.getUpdateMessages().forEach(msg -> sendMessage(e, msg));
            sendMessage(e, "If you don't want to see this message, edit "
                + "your configuration file to disable update checking.");
        }
    }

    private void sendMessage(EntityPlayer e, String message) {
        e.addChatComponentMessage(new TextComponentString(message));
    }
}
