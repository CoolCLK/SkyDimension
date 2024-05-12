package coolclk.skydimension.forge.world;

import coolclk.skydimension.forge.common.DimensionManager;

public class DimensionType {
    public static final net.minecraft.world.DimensionType SKY;

    static {
        SKY = net.minecraft.world.DimensionType.register("sky", "_sky", 2, WorldProviderSky.class, false);

        new DimensionManager();
    }
}
