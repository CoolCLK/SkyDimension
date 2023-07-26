package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Blocks;
import coolclk.skydimension.init.Items;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = SkyDimension.MOD_ID)
public class ClientRegistryEvent {
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        Items.registerModel();
        Blocks.registerModel();
    }
}
