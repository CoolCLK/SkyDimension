package coolclk.skydimension.forge.world;

import static net.minecraft.world.DimensionType.register;

/**
 * Dimension types.
 * @author CoolCLK
 */
public class DimensionType {
    public static final net.minecraft.world.DimensionType SKY;

    static {
        SKY = register("sky", "_sky", 2, WorldProviderSky.class, false);
    }
}
