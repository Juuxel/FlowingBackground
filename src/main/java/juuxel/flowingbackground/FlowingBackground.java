package juuxel.flowingbackground;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

@Environment(EnvType.CLIENT)
public final class FlowingBackground {
    public static final int SLOWNESS = 8;
    public static final int MAX_PROGRESS = 32;

    public static void render(MinecraftClient minecraft, int width, int height, float progress) {
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buf = t.getBuffer();

        minecraft.getTextureManager().bindTexture(Screen.BACKGROUND_LOCATION);
        RenderSystem.color4f(1, 1, 1, 1);
        buf.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
        buf.vertex(0, height + MAX_PROGRESS - progress, 0).color(64, 64, 64, 255).texture(0, (height + MAX_PROGRESS) / 32f).next();
        buf.vertex(width, height + MAX_PROGRESS - progress, 0).color(64, 64, 64, 255).texture(width / 32f, (height + MAX_PROGRESS) / 32f).next();
        buf.vertex(width, -progress, 0).color(64, 64, 64, 255).texture(width / 32f, 0).next();
        buf.vertex(0, -progress, 0).color(64, 64, 64, 255).texture(0, 0).next();

        t.draw();
    }
}
