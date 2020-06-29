package juuxel.flowingbackground.modmenu;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import juuxel.flowingbackground.config.ConfigScreen;

public final class ModMenuPlugin implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return ConfigScreen::new;
    }
}
