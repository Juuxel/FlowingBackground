package juuxel.flowingbackground;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Either;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Locale;
import java.util.Map;

public enum Direction {
    NORTH("north", 0, 1),
    NORTHEAST("northeast", -1, 1),
    EAST("east", -1, 0),
    SOUTHEAST("southeast", -1, -1),
    SOUTH("south", 0, -1),
    SOUTHWEST("southwest", 1, -1),
    WEST("west", 1, 0),
    NORTHWEST("northwest", 1, 1),
    ;

    private static final Map<String, Direction> BY_ID;

    static {
        ImmutableMap.Builder<String, Direction> byIdBuilder = ImmutableMap.builder();

        for (Direction value : values()) {
            byIdBuilder.put(value.getId(), value);
        }

        BY_ID = byIdBuilder.build();
    }

    private final String id;
    private final Text name;
    final int u;
    final int v;

    Direction(String id, int u, int v) {
        this.id = id;
        this.u = u;
        this.v = v;
        this.name = new TranslatableText("gui.flowing_background.config.direction." + name().toLowerCase(Locale.ROOT));
    }

    public String getId() {
        return id;
    }

    public Text getName() {
        return name;
    }

    public Direction cycle() {
        Direction[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    public static Either<String, Direction> byId(String id) {
        Direction direction = BY_ID.get(id);
        return direction != null ? Either.right(direction) : Either.left(id);
    }
}
