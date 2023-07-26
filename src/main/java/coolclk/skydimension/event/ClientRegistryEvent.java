package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Items;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = SkyDimension.MOD_ID)
public class ClientRegistryEvent {
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Items.SKY_ORE, 0, new ModelResourceLocation(Items.SKY_ORE.getRegistryName(), "normal"));
        ModelLoader.setCustomModelResourceLocation(Items.SKY_ORE, 0, new ModelResourceLocation(Items.SKY_ORE.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Items.SKY_INGOT, 0, new ModelResourceLocation(Items.SKY_INGOT.getRegistryName(), "inventory"));
    }
}
