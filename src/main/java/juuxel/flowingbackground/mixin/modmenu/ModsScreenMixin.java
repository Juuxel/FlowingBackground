package juuxel.flowingbackground.mixin.modmenu;

import io.github.prospector.modmenu.gui.ModsScreen;
import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModsScreen.class)
abstract class ModsScreenMixin {
    @Redirect(
        method = "overlayBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private static VertexConsumer flowingBackground_onOverlayBackground_onTexture(VertexConsumer vertexConsumer, float u, float v) {
        return FlowingBackground.handleTexture(vertexConsumer, u, v);
    }
}
