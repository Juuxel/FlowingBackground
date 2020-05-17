package juuxel.flowingbackground.mixin.cloth;

import juuxel.flowingbackground.FlowingBackground;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClothConfigScreen.class)
abstract class ClothConfigScreenMixin {
    @Redirect(
        method = "overlayBackground(Lnet/minecraft/util/math/Matrix4f;Lme/shedaniel/math/Rectangle;IIIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private VertexConsumer flowingBackground_onOverlayBackground_onTexture(VertexConsumer vertexConsumer, float u, float v) {
        float progress = FlowingBackground.getProgress();
        return vertexConsumer.texture(u, v + progress / 32f);
    }
}
