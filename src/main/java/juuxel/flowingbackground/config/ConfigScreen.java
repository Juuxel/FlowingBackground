package juuxel.flowingbackground.config;

import juuxel.flowingbackground.FlowingBackground;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.TranslatableText;

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
            I18n.translate("gui.done"),
            button -> {
                Config.save();
                minecraft.openScreen(parent);
            }
        ));

        addButton(new SliderWidget(width / 2 - 100, height / 2 - 20, 200, 20, speedToValue(FlowingBackground.speed)) {
            {
                updateMessage();
            }

            @Override
            protected void updateMessage() {
                setMessage(I18n.translate("gui.flowing_background.config.speed", SPEED_FORMAT.format(valueToSpeed(value))));
            }

            @Override
            protected void applyValue() {
                FlowingBackground.speed = valueToSpeed(value);
            }
        });
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderBackground();
        this.drawCenteredString(this.font, this.title.asFormattedString(), this.width / 2, 40, 0xFFFFFF);
        super.render(mouseX, mouseY, delta);
    }

    private static double speedToValue(float speed) {
        return (speed - MIN_SPEED) / (MAX_SPEED - MIN_SPEED);
    }

    private static float valueToSpeed(double value) {
        return (float) (value * (MAX_SPEED - MIN_SPEED) + MIN_SPEED);
    }
}
