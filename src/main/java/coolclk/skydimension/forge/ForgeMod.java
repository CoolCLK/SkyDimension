package coolclk.skydimension.forge;

import coolclk.skydimension.ModLoader;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.client.renderer.tileentity.TileEntityRendererHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;

/**
 * Entrance of the mod in Forge.
 * @author CoolCLK
 */
@Mod(SkyDimension.MOD_ID)
public class ForgeMod {
    @SubscribeEvent
    public static void beforeFMLInitialization(FMLCommonSetupEvent event) {
        SkyDimension.MOD_LOADER = ModLoader.FORGE;
        SkyDimension.MOD_LOGGER = LogManager.getLogger();

        // Make static registering active
        new TileEntityRendererHandler();
    }
}