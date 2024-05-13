package coolclk.skydimension.forge.common;

import coolclk.skydimension.forge.world.DimensionType;

/**
 * Managing dimensions.
 * @author CoolCLK
 */
public class DimensionManager {
    static {
        net.minecraftforge.common.DimensionManager.registerDimension(DimensionType.SKY.getId(), DimensionType.SKY);
    }
}
