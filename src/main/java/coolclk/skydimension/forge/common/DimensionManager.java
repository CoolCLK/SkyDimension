package coolclk.skydimension.forge.common;

import coolclk.skydimension.IObject;
import coolclk.skydimension.forge.world.DimensionType;

/**
 * Managing dimensions.
 * @author CoolCLK
 */
public class DimensionManager implements IObject {
    static {
        net.minecraftforge.common.DimensionManager.registerDimension(DimensionType.SKY.getId(), DimensionType.SKY);
    }
}
