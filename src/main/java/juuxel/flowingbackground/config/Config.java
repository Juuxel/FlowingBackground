package juuxel.flowingbackground.config;

import juuxel.flowingbackground.Direction;
import juuxel.flowingbackground.FlowingBackground;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Function;

public final class Config {
    private static final Logger LOGGER = LogManager.getLogger("FlowingBackground|Config");
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDirectory().toPath().resolve("FlowingBackground.properties");
    private static final String KEY_SPEED = "speed";
    private static final String KEY_REPLACE_TITLE_SCREEN = "replace-title-screen";
    private static final String KEY_DIRECTION = "direction";

    private Config() {}

    private static Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty(KEY_SPEED, String.valueOf(FlowingBackground.speed));
        properties.setProperty(KEY_REPLACE_TITLE_SCREEN, String.valueOf(FlowingBackground.replaceTitleScreen));
        properties.setProperty(KEY_DIRECTION, FlowingBackground.direction.getId());

        return properties;
    }

    public static void init() {
        if (Files.notExists(CONFIG_PATH)) {
            save();
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH, StandardCharsets.UTF_8)) {
            Properties properties = createProperties();
            properties.load(reader);

            FlowingBackground.speed = MathHelper.clamp(Float.parseFloat(properties.getProperty(KEY_SPEED)), FlowingBackground.MIN_SPEED, FlowingBackground.MAX_SPEED);
            FlowingBackground.replaceTitleScreen = Boolean.parseBoolean(properties.getProperty(KEY_REPLACE_TITLE_SCREEN));
            FlowingBackground.direction = Direction.byId(properties.getProperty(KEY_DIRECTION)).map(id -> {
                LOGGER.warn("Unknown direction '{}', defaulting to north", id);
                return Direction.NORTH;
            }, Function.identity());
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
