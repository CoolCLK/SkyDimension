package coolclk.skydimension.forge.world;

import coolclk.skydimension.IObject;

/**
 * Dimension types.
 * @author CoolCLK
 */
public class DimensionType implements IObject {
    public static final net.minecraft.world.DimensionType SKY;

    static {
        SKY = net.minecraft.world.DimensionType.register("sky", "_sky", 2, WorldProviderSky.class, false);
    }
}
