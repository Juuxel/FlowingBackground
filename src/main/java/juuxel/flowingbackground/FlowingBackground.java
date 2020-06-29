package juuxel.flowingbackground;

import juuxel.flowingbackground.config.Config;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.VertexConsumer;

public final class FlowingBackground implements ClientModInitializer {
    public static final int SLOWNESS = 8;
    public static final int MAX_PROGRESS = 32;
    public static final float MIN_SPEED = 0f;
    public static final float MAX_SPEED = 4f;
    public static float speed = 1.0f;
    public static Direction direction = Direction.NORTH;
    public static boolean replaceTitleScreen = false;
    private static int progress = 0;

    @Override
    public void onInitializeClient() {
        Config.init();
    }

    public static void incrementProgress() {
        progress++;

        if (progress >= MAX_PROGRESS * SLOWNESS / speed) {
            progress = 0;
        }
    }

    public static float getProgress() {
        return (float) progress / (float) SLOWNESS * speed;
    }

    public static VertexConsumer handleTexture(VertexConsumer vertexConsumer, float u, float v) {
        float p = getProgress() / 32f;
        return vertexConsumer.texture(u + p * direction.u, v + p * direction.v);
    }
}
