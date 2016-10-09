package notjoe.pointersmod.common.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import notjoe.pointersmod.PointersMod;
import notjoe.pointersmod.common.Config;

public class ModEvents {
    @SubscribeEvent public void onPlayerJoin(EntityJoinWorldEvent event) {
        if (Config.enableUpdateCheck && event.getEntity() instanceof EntityPlayer && event
            .getWorld().isRemote && !PointersMod.updateStatus.isLatestVersion) {
            EntityPlayer e = (EntityPlayer) event.getEntity();
            if(PointersMod.updateStatus.getLatestRelease().isEmpty()) {
                sendMessage(e, "No " + PointersMod.updateStatus.releaseChannel.toUpperCase() + " version of Pointers exists yet!");
                sendMessage(e, "Latest ALPHA: " + (PointersMod.updateStatus.latestAlpha.isEmpty()? "None": PointersMod.updateStatus.latestAlpha));
                sendMessage(e, "Latest BETA: " + (PointersMod.updateStatus.latestBeta.isEmpty()? "None": PointersMod.updateStatus.latestBeta));
                sendMessage(e, "Latest RELEASE: " + (PointersMod.updateStatus.latestRelease.isEmpty()? "None": PointersMod.updateStatus.latestRelease));
            } else {
                sendMessage(e, "A new " + PointersMod.updateStatus.releaseChannel.toUpperCase() + " version of Pointers is available! " + PointersMod.updateStatus.getLatestRelease());
            }

            sendMessage(e, "If you don't want to see this message, edit your configuration file to disable update checking.");
        }
    }

    private void sendMessage(EntityPlayer e, String message) {
        e.addChatComponentMessage(new TextComponentString(message));
    }
}
