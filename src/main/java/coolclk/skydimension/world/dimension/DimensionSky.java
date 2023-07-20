package coolclk.skydimension.world.dimension;

import coolclk.skydimension.world.WorldSky;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class DimensionSky {
    private static DimensionType dimensionType;
    private final static int dimensionId = 2;
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_" + dimensionName;
    private static World dimensionWorld;
    private static WorldProvider dimensionWorldProvider;

    public static int getId() {
        return dimensionId;
    }

    public static DimensionType getType() {
        return dimensionType;
    }

    public static String getName() {
        return dimensionName;
    }

    public static String getSuffix() {
        return dimensionSuffix;
    }

    public static WorldProvider getWorldProvider() {
        if (dimensionWorldProvider == null) {
            dimensionWorldProvider = DimensionSky.getType().createDimension();
        }
        return dimensionWorldProvider;
    }

    public static World getWorld() {
        if (dimensionWorld == null) {
            dimensionWorld = new WorldSky();
            DimensionSky.getWorldProvider().setWorld(dimensionWorld);
        }
        return dimensionWorld;
    }

    public static void setType(DimensionType type) {
        dimensionType = type;
    }
}
