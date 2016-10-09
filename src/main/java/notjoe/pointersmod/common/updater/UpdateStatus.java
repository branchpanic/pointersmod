package notjoe.pointersmod.common.updater;

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
        this.isLatestVersion = getLatestRelease().equals(PointersMod.VERSION);
    }

    public static UpdateStatus failedUpdate() {
        return new UpdateStatus("unknown", "", "", "");
    }

    public String getLatestRelease() {
        if(releaseChannel.equals("alpha")) return latestAlpha;
        if(releaseChannel.equals("beta")) return latestBeta;
        if(releaseChannel.equals("release")) return latestRelease;
        return "Unknown";
    }
}
