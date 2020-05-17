package juuxel.flowingbackground.mixin.cloth;

import juuxel.flowingbackground.FlowingBackground;
import me.shedaniel.clothconfig2.gui.widget.DynamicEntryListWidget;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DynamicEntryListWidget.class)
abstract class DynamicEntryListWidgetMixin {
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
        method = "renderBackBackground",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private VertexConsumer flowingBackground_onRenderBackBackground_onTexture(VertexConsumer vertexConsumer, float u, float v) {
        float progress = FlowingBackground.getProgress();
        return vertexConsumer.texture(u, v + progress / 32f);
    }
}
