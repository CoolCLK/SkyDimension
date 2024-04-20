package coolclk.skydimension.forge;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.event.EventHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SkyDimension.MOD_ID)
public class ForgeMod {
    public static Logger LOGGER;

    @Mod.EventHandler
    public static void beforeFMLInitialization(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        LOGGER.info("========== [Sky Dimension] ==========");
        LOGGER.info("The mod successfully launched!");
        LOGGER.info("Author: CoolCLK");
        LOGGER.info("Thanks for your using!");
        LOGGER.info("=====================================");

        EventHandler.beforeFMLInitialization();
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        EventHandler.onServerStarting(event);
    }
}