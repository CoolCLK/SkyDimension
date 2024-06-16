package coolclk.skydimension.forge.world;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;

import static net.minecraftforge.common.DimensionManager.registerOrGetDimension;

/**
 * Dimension types with Forge.
 * @author CoolCLK
 */
public class DimensionType {
    public static final net.minecraft.world.dimension.DimensionType SKY = registerOrGetDimension(new ResourceLocation(SkyDimension.MOD_ID, "sky"), ModDimension.SKY, null, true);
}
