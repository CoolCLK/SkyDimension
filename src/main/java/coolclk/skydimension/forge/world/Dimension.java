package coolclk.skydimension.forge.world;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Dimension {
    public static final RegistryKey<net.minecraft.world.Dimension> SKY = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(SkyDimension.MOD_ID, "sky"));
}
