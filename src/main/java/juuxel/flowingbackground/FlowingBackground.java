package juuxel.flowingbackground;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FlowingBackground {
    public static final int SLOWNESS = 8;
    public static final int MAX_PROGRESS = 32;
    private static int progress = 0;

    public static void incrementProgress() {
        progress++;

        if (progress >= MAX_PROGRESS * SLOWNESS) {
            progress = 0;
        }
    }

    public static float getProgress() {
        return (float) progress / (float) SLOWNESS;
    }
}
