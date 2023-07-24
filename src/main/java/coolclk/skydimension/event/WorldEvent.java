package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void afterOreGenEvent(OreGenEvent.Post event) {

    }
}
