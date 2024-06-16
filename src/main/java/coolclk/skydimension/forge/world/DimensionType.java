package coolclk.skydimension.forge.world;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;


/**
 * Dimension types.
 * @author CoolCLK
 */
public class DimensionType implements IObject {
    @SuppressWarnings("deprecation")
    public static final net.minecraft.world.dimension.DimensionType SKY = register(new ResourceLocation(SkyDimension.MOD_ID, "sky"), new net.minecraft.world.dimension.DimensionType(1, "_sky", "DIM2", coolclk.skydimension.forge.world.dimension.SkyDimension::new, false, null, null));

    @SuppressWarnings("deprecation")
    private static net.minecraft.world.dimension.DimensionType register(ResourceLocation location, net.minecraft.world.dimension.DimensionType dimensionType) {
        return Registry.register(Registry.DIMENSION_TYPE, location, dimensionType);
    }
}
