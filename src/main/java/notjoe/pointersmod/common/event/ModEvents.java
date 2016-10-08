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
            .getWorld().isRemote && !PointersMod.updateStatusFormatted.isEmpty()) {
            ((EntityPlayer) event.getEntity()).addChatComponentMessage(
                new TextComponentString(PointersMod.updateStatusFormatted));
        }
    }
}
