package coolclk.skydimension.forge.world.dimension;

import coolclk.skydimension.forge.world.provider.WorldProviderSky;
import coolclk.skydimension.forge.world.teleporter.TeleporterSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import static coolclk.skydimension.forge.ForgeMod.LOGGER;

public class DimensionSky {
    private static int dimensionId = getDimensionIdValidOrChange(2);
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_dim";
    private static DimensionType dimensionType;

    public static void registry() {
        dimensionType = DimensionType.register(DimensionSky.getDimensionName(), DimensionSky.getDimensionSuffix(), DimensionSky.getDimensionId(), WorldProviderSky.class, false);
        DimensionManager.registerDimension(DimensionSky.getDimensionId(), dimensionType);
    }

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

    protected static int getDimensionIdValidOrChange(int i) {
        try {
            DimensionType.getById(i);
        } catch (IllegalArgumentException e) {
            return i;
        }
        return getDimensionIdValidOrChange(i + 1);
    }

    public static void letPlayerGoDimension(EntityPlayer player) {
        if (player.dimension != DimensionSky.getDimensionId()) {
            LOGGER.debug("Sending player to dimension.");
            if (player.changeDimension(DimensionSky.getDimensionId(), new TeleporterSky()) != null) {
                LOGGER.debug("Sent player successfully.");
            }
        }
    }
}
