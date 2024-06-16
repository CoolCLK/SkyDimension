package coolclk.skydimension.forge.world;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

/**
 * Dimension types.
 * @author CoolCLK
 */
public class DimensionType {
    @SuppressWarnings("deprecation")
    public static final net.minecraft.world.dimension.DimensionType SKY = register(new ResourceLocation(SkyDimension.MOD_ID, "sky"), new net.minecraft.world.dimension.DimensionType(2, "_sky", "DIM2", coolclk.skydimension.forge.world.dimension.SkyDimension::new, true, ModDimension.SKY, null));

    @SuppressWarnings("deprecation")
    private static net.minecraft.world.dimension.DimensionType register(ResourceLocation p_212677_0_, net.minecraft.world.dimension.DimensionType p_212677_1_) {
        return Registry.register(Registry.DIMENSION_TYPE, p_212677_1_.getId(), p_212677_0_.getNamespace(), p_212677_1_);
    }
}
