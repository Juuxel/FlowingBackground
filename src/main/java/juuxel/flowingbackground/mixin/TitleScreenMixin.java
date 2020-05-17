package juuxel.flowingbackground.mixin;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin extends Screen {
    private TitleScreenMixin() {
        super(null);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/RotatingCubeMapRenderer;render(FF)V"))
    private void flowingBackground_onRender(RotatingCubeMapRenderer renderer, float delta, float alpha) {
        if (FlowingBackground.replaceTitleScreen) {
            renderBackgroundTexture(0);
        } else {
            renderer.render(delta, alpha);
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIFFIIII)V", ordinal = 0))
    private void flowingBackground_onRender_onDrawTexture(MatrixStack matrices, int x, int y, int width, int height, float u, float v, int uWidth, int vHeight, int texWidth, int texHeight) {
        // Disable panorama overlay rendering when the title screen panorama is replaced
        if (!FlowingBackground.replaceTitleScreen) {
            drawTexture(matrices, x, y, width, height, u, v, uWidth, vHeight, texWidth, texHeight);
        }
    }
}
