package juuxel.flowingbackground.config;

import juuxel.flowingbackground.FlowingBackground;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Config {
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("FlowingBackground.properties");
    private static final String KEY_SPEED = "speed";
    private static final String KEY_REPLACE_TITLE_SCREEN = "replace-title-screen";

    private Config() {}

    private static Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty(KEY_SPEED, String.valueOf(FlowingBackground.speed));
        properties.setProperty(KEY_REPLACE_TITLE_SCREEN, String.valueOf(FlowingBackground.replaceTitleScreen));

        return properties;
    }

    public static void init() {
        if (Files.notExists(CONFIG_PATH)) {
            save();
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
            Properties properties = createProperties();
            properties.load(reader);

            FlowingBackground.speed = Float.parseFloat(properties.getProperty(KEY_SPEED));
            FlowingBackground.replaceTitleScreen = Boolean.parseBoolean(properties.getProperty(KEY_REPLACE_TITLE_SCREEN));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not load Flowing Background config file!", e);
        }
    }

    public static void save() {
        Properties properties = createProperties();

        try (Writer writer = Files.newBufferedWriter(CONFIG_PATH, StandardCharsets.UTF_8)) {
            properties.store(writer, null);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not save Flowing Background config file!", e);
        }
    }
}
