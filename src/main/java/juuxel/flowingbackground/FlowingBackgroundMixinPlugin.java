package juuxel.flowingbackground;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;

public final class FlowingBackgroundMixinPlugin implements IMixinConfigPlugin {
    private static final BooleanSupplier TRUE = () -> true;

    private static final ImmutableMap<String, BooleanSupplier> CONDITIONS =
        ImmutableMap.of(
            "juuxel.flowingbackground.mixin.modmenu.ModsScreenMixin",
            () -> FabricLoader.getInstance().isModLoaded("modmenu"),
            "juuxel.flowingbackground.mixin.cloth.DynamicEntryListWidgetMixin",
            () -> FabricLoader.getInstance().isModLoaded("cloth-config2")
        );

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return CONDITIONS.getOrDefault(mixinClassName, TRUE).getAsBoolean();
    }

    ////////////////////////////////////////////
    //           Boilerplate below!           //
    ////////////////////////////////////////////

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
