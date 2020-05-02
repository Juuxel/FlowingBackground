package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
abstract class GameRendererMixin {
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;render(IIF)V"))
    private void flowingBackground_onRender(float tickDelta, long startTime, boolean tick, CallbackInfo info) {
        FlowingBackground.incrementProgress();
    }
}
