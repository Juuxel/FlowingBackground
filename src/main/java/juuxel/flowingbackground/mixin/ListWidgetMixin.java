package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.widget.ListWidget;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ListWidget.class)
abstract class ListWidgetMixin {
    @Redirect(
        method = "renderHoleBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private VertexConsumer flowingBackground_onRenderHoleBackground_onTexture(VertexConsumer vertexConsumer, float u, float v) {
        float progress = FlowingBackground.getProgress();
        return vertexConsumer.texture(u, v + progress / 32f);
    }

    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        ),
        slice = @Slice(
            to = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Tessellator;draw()V", ordinal = 0)
        )
    )
    private VertexConsumer flowingBackground_onRender_redirectTexture(VertexConsumer vertexConsumer, float u, float v) {
        float progress = FlowingBackground.getProgress();
        return vertexConsumer.texture(u, v + progress / 32f);
    }
}
