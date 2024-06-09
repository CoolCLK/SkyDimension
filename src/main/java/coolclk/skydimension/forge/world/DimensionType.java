package coolclk.skydimension.forge.world;

import coolclk.skydimension.IObject;

import static net.minecraft.world.DimensionType.register;

/**
 * Dimension types.
 * @author CoolCLK
 */
public class DimensionType implements IObject {
    public static final net.minecraft.world.DimensionType SKY;

    static {
        SKY = register("sky", "_sky", 2, WorldProviderSky.class, false);
    }
}
