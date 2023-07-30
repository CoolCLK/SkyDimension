package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void onVillageSiege(VillageSiegeEvent event) {
        if (event.getWorld().provider.getDimensionType() == DimensionSky.getDimensionType()) {
            event.setCanceled(true);
        }
    }
}
