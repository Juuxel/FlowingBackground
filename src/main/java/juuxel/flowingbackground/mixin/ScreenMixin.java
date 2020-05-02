package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
abstract class ScreenMixin {
    @Shadow
    protected MinecraftClient minecraft;

    @Shadow
    public int width;

    @Shadow
    public int height;

    @Unique
    private int flowingBackgroundProgress;

    @Inject(method = "renderDirtBackground", at = @At("HEAD"), cancellable = true)
    private void flowingBackground_onRenderDirtBackground(int yOffset, CallbackInfo info) {
        if (flowingBackgroundProgress >= FlowingBackground.MAX_PROGRESS * FlowingBackground.SLOWNESS) {
            flowingBackgroundProgress = 0;
        }

        FlowingBackground.render(minecraft, width, height, (float) flowingBackgroundProgress / (float) FlowingBackground.SLOWNESS);
        flowingBackgroundProgress++;

        info.cancel();
    }
}
