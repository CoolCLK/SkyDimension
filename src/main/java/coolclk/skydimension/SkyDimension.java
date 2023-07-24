package coolclk.skydimension;

import coolclk.skydimension.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SkyDimension.MOD_ID)
public class SkyDimension {
    public final static String MOD_ID = "skydimension";

    public static Logger LOGGER;

    @EventHandler
    public static void beforeFMLInitialization(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        LOGGER.info("========== [Sky Dimension] ==========");
        LOGGER.info("The mod successfully launched!");
        LOGGER.info("Author: CoolCLK");
        LOGGER.info("Thanks for your using!");
        LOGGER.info("=====================================");

        RegistryEvent.beforeFMLPreInitializationEvent(event);
    }

    @EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        RegistryEvent.onServerStarting(event);
    }
}