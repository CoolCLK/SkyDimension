package coolclk.skydimension.forge.world.gen.feature.structure;

import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public class Feature {
    public static final Structure<NoFeatureConfig> STRONGHOLD = new StrongholdPortalRoomStructure(NoFeatureConfig::deserialize);
    public static final Structure<NoFeatureConfig> FLOATING_SHIP = new FloatingShipStructure(NoFeatureConfig::deserialize);
}
