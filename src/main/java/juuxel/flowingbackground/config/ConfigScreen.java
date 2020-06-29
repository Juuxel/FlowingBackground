package juuxel.flowingbackground.config;

import juuxel.flowingbackground.Direction;
import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.text.DecimalFormat;

import static juuxel.flowingbackground.FlowingBackground.MAX_SPEED;
import static juuxel.flowingbackground.FlowingBackground.MIN_SPEED;

public final class ConfigScreen extends Screen {
    private static final DecimalFormat SPEED_FORMAT = new DecimalFormat("#.##");
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(new TranslatableText("gui.flowing_background.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        addButton(new ButtonWidget(
            width / 2 - 100,
            height / 2 + 40,
            200, 20,
            new TranslatableText("gui.done"),
            button -> {
                Config.save();
                client.openScreen(parent);
            }
        ));

        double initialSpeed = speedToValue(FlowingBackground.speed);
        addButton(new SliderWidget(width / 2 - 100, height / 2 - 20, 200, 20, getSpeedMessage(initialSpeed), initialSpeed) {
            {
                updateMessage();
            }

            @Override
            protected void updateMessage() {
                setMessage(ConfigScreen.getSpeedMessage(value));
            }

            @Override
            protected void applyValue() {
                FlowingBackground.speed = valueToSpeed(value);
            }
        });

        addButton(new ButtonWidget(
            width / 2 - 100,
            height / 2 - 45,
            200, 20,
            getPanoramaMessage(FlowingBackground.replaceTitleScreen),
            button -> {
                FlowingBackground.replaceTitleScreen = !FlowingBackground.replaceTitleScreen;
                button.setMessage(getPanoramaMessage(FlowingBackground.replaceTitleScreen));
            }
        ));

        addButton(new ButtonWidget(width / 2 - 100, height / 2 + 5, 200, 20, getDirectionMessage(), button -> {
            FlowingBackground.direction = FlowingBackground.direction.cycle();
            button.setMessage(getDirectionMessage());
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackgroundTexture(0);
        drawCenteredText(matrices, textRenderer, title, this.width / 2, 40, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private static Text getSpeedMessage(double value) {
        return new TranslatableText("gui.flowing_background.config.speed", SPEED_FORMAT.format(valueToSpeed(value)));
    }

    private static Text getPanoramaMessage(boolean value) {
        return new TranslatableText(
            "gui.flowing_background.config.replace_title_screen",
            new TranslatableText(value ? "options.on" : "options.off").formatted(value ? Formatting.GREEN : Formatting.RED)
        );
    }

    private static Text getDirectionMessage() {
        Direction direction = FlowingBackground.direction;
        return new TranslatableText(
            "gui.flowing_background.config.direction",
            direction.getName().copy().formatted(Formatting.values()[direction.ordinal() + 7])
        );
    }

    private static double speedToValue(float speed) {
        return (speed - MIN_SPEED) / (MAX_SPEED - MIN_SPEED);
    }

    private static float valueToSpeed(double value) {
        return (float) (value * (MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }
}
