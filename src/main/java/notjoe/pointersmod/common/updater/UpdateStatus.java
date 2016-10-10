package notjoe.pointersmod.common.updater;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import notjoe.pointersmod.PointersMod;

public class UpdateStatus {
    public final String releaseChannel;
    public final String latestAlpha;
    public final String latestBeta;
    public final String latestRelease;
    public final boolean isLatestVersion;

    public UpdateStatus(String releaseChannel, String latestAlpha, String latestBeta, String latestRelease) {
        this.releaseChannel = releaseChannel;
        this.latestAlpha = latestAlpha;
        this.latestBeta = latestBeta;
        this.latestRelease = latestRelease;
        this.isLatestVersion = getLatestReleaseCurrentChannel().equals(PointersMod.VERSION);
    }

    public static UpdateStatus failedUpdate() {
        return new UpdateStatus("unknown", "", "", "");
    }

    public String getLatestReleaseCurrentChannel() {
        if(releaseChannel.equals("alpha")) return latestAlpha;
        if(releaseChannel.equals("beta")) return latestBeta;
        if(releaseChannel.equals("release")) return latestRelease;
        return "Unknown";
    }

    public List<String> getUpdateMessages() {
        if(isLatestVersion) {
            return Collections.singletonList("Pointers is up to date.");
        } else if(getLatestReleaseCurrentChannel().equals("") ||
                getLatestReleaseCurrentChannel().equals("Unknown")) {
            return Arrays.asList(
                    String.format("No versions of Pointers were found on channel: %S", releaseChannel),
                    String.format("The latest ALPHA release is %s.", latestAlpha),
                    String.format("The latest BETA release is %s.", latestBeta),
                    String.format("The latest STABLE release is %s.", latestRelease),
                    "Note that the alpha and beta releases, as they are used in development, may be behind the latest stable release."
            );
        } else {
            return Arrays.asList(
                    String.format("A new version of pointers is available! %s", getLatestReleaseCurrentChannel()),
                    "Check out the CurseForge page for a changelog."
            );
        }
    }
}
