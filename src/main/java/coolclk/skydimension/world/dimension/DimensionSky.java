package coolclk.skydimension.world.dimension;

import coolclk.skydimension.world.provider.WorldProviderSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;

import static coolclk.skydimension.SkyDimension.LOGGER;

public class DimensionSky {
    private final static int dimensionId = 2;
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_" + dimensionName;
    private final static DimensionType dimensionType = DimensionType.register(DimensionSky.getDimensionName(), DimensionSky.getDimensionSuffix(), DimensionSky.getDimensionId(), WorldProviderSky.class, false);
    public static int getDimensionId() {
        return dimensionId;
    }

    public static DimensionType getDimensionType() {
        return dimensionType;
    }

    public static String getDimensionName() {
        return dimensionName;
    }

    public static String getDimensionSuffix() {
        return dimensionSuffix;
    }

    public static void letPlayerGoDimension(EntityPlayer player) {
        if (player.dimension != DimensionSky.getDimensionId()) {
            if (player.changeDimension(DimensionSky.getDimensionId()) != null) {
                LOGGER.debug("Sending player to dimension.");
            }
        }
    }
}
