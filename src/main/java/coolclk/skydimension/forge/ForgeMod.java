package coolclk.skydimension.forge;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.event.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SkyDimension.MOD_ID)
public class ForgeMod {
    public static Logger LOGGER;

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        EventHandler.onServerStarting(event);
    }
}