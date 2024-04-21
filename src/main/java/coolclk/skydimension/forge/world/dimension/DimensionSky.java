package coolclk.skydimension.forge.world.dimension;

import coolclk.skydimension.forge.world.provider.WorldProviderSky;
import coolclk.skydimension.forge.world.teleporter.TeleporterSky;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.DimensionType;

import static coolclk.skydimension.forge.ForgeMod.LOGGER;

public class DimensionSky {
    private static int dimensionId = getDimensionIdValidOrChange(2);
    private final static String dimensionName = "sky";
    private final static String dimensionSuffix = "_dim";
    private static DimensionType dimensionType;

    public static void registry() {
        /*
        * this.fixedTime = p_i241973_1_;
      this.hasSkylight = p_i241973_2_;
      this.hasCeiling = p_i241973_3_;
      this.ultraWarm = p_i241973_4_;
      this.natural = p_i241973_5_;
      this.coordinateScale = p_i241973_6_;
      this.createDragonFight = p_i241973_8_;
      this.piglinSafe = p_i241973_9_;
      this.bedWorks = p_i241973_10_;
      this.respawnAnchorWorks = p_i241973_11_;
      this.hasRaids = p_i241973_12_;
      this.logicalHeight = p_i241973_13_;
      this.biomeZoomer = p_i241973_14_;
      this.infiniburn = p_i241973_15_;
      this.effectsLocation = p_i241973_16_;
      this.ambientLight = p_i241973_17_;
      this.brightnessRamp = fillBrightnessRamp(p_i241973_17_);*/
        dimensionType = new DimensionType(
                DimensionSky.getDimensionName(),
                DimensionSky.getDimensionSuffix(),
                DimensionSky.getDimensionId(),
                WorldProviderSky.class,
                false
        );
        Dimension.registerDimension(DimensionSky.getDimensionId(), dimensionType);
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

    public static void letPlayerGoDimension(PlayerEntity player) {
        if (player.dimension != DimensionSky.getDimensionId()) {
            LOGGER.debug("Sending player to dimension.");
            if (player.changeDimension(, new TeleporterSky()) != null) {
                LOGGER.debug("Sent player successfully.");
            }
        }
    }
}
