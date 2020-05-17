package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Screen.class)
abstract class ScreenMixin {
    @Redirect(
        method = "renderBackgroundTexture",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private VertexConsumer flowingBackground_onRenderBackgroundTexture(VertexConsumer vertexConsumer, float u, float v) {
        float progress = FlowingBackground.getProgress();
        return vertexConsumer.texture(u, v + progress / 32f);
    }
}
