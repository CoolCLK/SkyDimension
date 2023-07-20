package coolclk.skydimension.world.dimension;

import coolclk.skydimension.world.WorldSky;
import coolclk.skydimension.world.provider.WorldProviderSky;
import net.minecraft.client.Minecraft;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class DimensionSky {
    private static DimensionType dimensionType;
    private final static int dimensionId = 2;
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_" + dimensionName;
    private static World dimensionWorld;

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

    public static World getWorld() {
        dimensionWorld = new WorldSky(Minecraft.getMinecraft().world.getSaveHandler(), Minecraft.getMinecraft().world.getWorldInfo(), new WorldProviderSky(), Minecraft.getMinecraft().mcProfiler, Minecraft.getMinecraft().world.isRemote);
        DimensionType.getById(DimensionSky.getId()).createDimension().setWorld(dimensionWorld);
        return dimensionWorld;
    }

    public static void setType(DimensionType type) {
        dimensionType = type;
    }
}
