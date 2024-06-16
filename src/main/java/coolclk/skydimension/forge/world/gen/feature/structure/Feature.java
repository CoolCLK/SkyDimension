package coolclk.skydimension.forge.world.gen.feature.structure;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class Feature {
    public static final Structure<NoFeatureConfig> STRONGHOLD = register(new ResourceLocation(SkyDimension.MOD_ID, "stronghold"), new StrongholdPortalRoomStructure(NoFeatureConfig::deserialize));
    public static final Structure<NoFeatureConfig> FLOATING_SHIP = register(new ResourceLocation(SkyDimension.MOD_ID, "floating_boat"), new FloatingShipStructure(NoFeatureConfig::deserialize));

    @SuppressWarnings({"deprecation", "SameParameterValue"})
    private static <C extends IFeatureConfig, F extends net.minecraft.world.gen.feature.Feature<C>> F register(ResourceLocation location, F feature) {
        return Registry.register(Registry.FEATURE, location, feature);
    }
}
