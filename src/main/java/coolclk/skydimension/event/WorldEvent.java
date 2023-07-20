package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import coolclk.skydimension.world.generate.WorldGeneratorSky;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void onPlayerSleepInBed(OreGenEvent.Post event) {
        if (event.getWorld() == DimensionSky.getWorld()) {
            new WorldGeneratorSky().generate(event.getWorld(), event.getRand(), event.getPos());
        }
    }
}
