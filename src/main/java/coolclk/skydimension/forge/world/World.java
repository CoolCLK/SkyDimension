package coolclk.skydimension.forge.world;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class World {
    public static final RegistryKey<net.minecraft.world.World> SKY = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(SkyDimension.MOD_ID, "sky"));
}
