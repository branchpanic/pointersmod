package notjoe.pointersmod.common.updater;

import com.google.gson.Gson;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.resources.I18n;
import notjoe.pointersmod.PointersMod;

import java.awt.*;
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

    public UpdateStatus checkForUpdates() {
        PointersMod.logger.info("Beginning update check");
        InputStream is;
        try {
            is = new URL(
                "https://raw.githubusercontent.com/notjoe7F/pointersmod/master/release-info.json")
                .openStream();
        } catch (IOException e) {
            e.printStackTrace();
            PointersMod.logger.error("Couldn't check for updates.");
            return UpdateStatus.failedUpdate();
        }

        BufferedReader reader =
            new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        Map jsonData = new Gson().fromJson(reader, Map.class);

        UpdateStatus status = new UpdateStatus(
            releaseType,
            jsonData.get("alpha").toString(),
            jsonData.get("beta").toString(),
            jsonData.get("release").toString()
        );

        try {
            reader.close();
            is.close();
        } catch (IOException e) {
            PointersMod.logger.warn("An error occurred while finishing update checking. Its stack trace follows.");
            e.printStackTrace();
        }

        return status;
    }
}
