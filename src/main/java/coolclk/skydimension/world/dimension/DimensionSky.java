package coolclk.skydimension.world.dimension;

import coolclk.skydimension.world.provider.WorldProviderSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

import static coolclk.skydimension.SkyDimension.LOGGER;

public class DimensionSky {
    private final static int dimensionId = 2;
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_" + dimensionName;
    private final static DimensionType dimensionType = DimensionType.register(DimensionSky.getName(), DimensionSky.getSuffix(), DimensionSky.getId(), WorldProviderSky.class, false);
    private final static WorldProvider dimensionWorldProvider = dimensionType.createDimension();
    private final static World dimensionWorld = ((WorldProviderSky) dimensionWorldProvider).getWorld();
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
        return dimensionWorld;
    }

    public static void go(EntityPlayer player) {
        if (player.getEntityWorld() != DimensionSky.getWorld()) {
            player.setWorld(DimensionSky.getWorld());
            LOGGER.debug("Sending player to dimension.");
        }
    }
}
