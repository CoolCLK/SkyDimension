package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RecipeEvent {
    static {
        RegistryEvent.registrySmelting();
    }

    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
    }
}
