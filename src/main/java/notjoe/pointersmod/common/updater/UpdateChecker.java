package notjoe.pointersmod.common.updater;

import com.google.gson.Gson;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import notjoe.pointersmod.PointersMod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

public class UpdateChecker {
    private String releaseType;

    public UpdateChecker(String releaseType) {
        this.releaseType = releaseType;
    }

    public void checkForUpdates() {
        InputStream is;
        try {
            is = new URL("https://raw.githubusercontent.com/notjoe7F/pointersmod/master/release-info.json").openStream();
        } catch (IOException e) {
            e.printStackTrace();
            PointersMod.updateStatusFormatted = I18n.format("pointers.update.error");
            PointersMod.updateStatusPlain = PointersMod.updateStatusFormatted;
            return;
        }

        BufferedReader reader =
            new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        Map jsonData = new Gson().fromJson(reader, Map.class);
        if (jsonData.containsKey(releaseType)) {
            if(!jsonData.get(releaseType).equals("")) {
                if (jsonData.get(releaseType).equals(PointersMod.VERSION)) {
                    PointersMod.updateStatusFormatted = I18n.format("pointers.update.uptodate");
                    PointersMod.updateStatusPlain = PointersMod.updateStatusFormatted;
                } else {
                    PointersMod.updateStatusFormatted =
                        I18n.format("pointers.update", color(releaseType),
                            color((String) jsonData.get(releaseType)));
                    PointersMod.updateStatusPlain =
                        I18n.format("pointers.update", releaseType, jsonData.get(releaseType));
                }
            } else {
                PointersMod.updateStatusFormatted =
                    I18n.format("pointers.update.nover", color(releaseType));
                PointersMod.updateStatusPlain =
                    I18n.format("pointers.update.nover", releaseType);
            }
        }
        try {
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            PointersMod.updateStatusFormatted = I18n.format("pointers.update.ioerror");
            PointersMod.updateStatusPlain = PointersMod.updateStatusFormatted;
        }
    }

    private String color(String original) {
        return ChatFormatting.AQUA + original + ChatFormatting.RESET;
    }
}
