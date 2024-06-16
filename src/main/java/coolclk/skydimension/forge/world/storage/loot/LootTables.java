package coolclk.skydimension.forge.world.storage.loot;

import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;

public class LootTables {
    public static final ResourceLocation CHESTS_FLOATING_BOAT = register(new ResourceLocation(SkyDimension.MOD_ID, "chests/floating_boat_treasure"));

    /**
     * Useless thing.
     * @author CoolCLK
     */
    private static ResourceLocation register(ResourceLocation resourceLocation) {
        return resourceLocation;
    }
}
