package coolclk.skydimension.world.dimension;

import coolclk.skydimension.world.provider.WorldProviderSky;
import coolclk.skydimension.world.teleporter.TeleporterSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraft.world.Teleporter;
import net.minecraftforge.common.DimensionManager;

import static coolclk.skydimension.SkyDimension.LOGGER;

public class DimensionSky {
    private final static int dimensionId = 2;
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_dim" + dimensionId;
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

    public static void letPlayerGoDimension(EntityPlayer player) {
        if (player.dimension != DimensionSky.getDimensionId()) {
            LOGGER.debug("Sending player to dimension.");
            if (player.changeDimension(DimensionSky.getDimensionId(), new TeleporterSky()) != null) {
                LOGGER.debug("Sent player successfully.");
            }
        }
    }
}
