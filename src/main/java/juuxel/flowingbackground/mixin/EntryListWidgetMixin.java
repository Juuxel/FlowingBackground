package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntryListWidget.class)
abstract class EntryListWidgetMixin {
    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/VertexConsumer;texture(FF)Lnet/minecraft/client/render/VertexConsumer;"
        )
    )
    private VertexConsumer flowingBackground_onRender_redirectTexture(VertexConsumer vertexConsumer, float u, float v) {
        return FlowingBackground.handleTexture(vertexConsumer, u, v);
    }
}
