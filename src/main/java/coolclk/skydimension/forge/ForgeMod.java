package coolclk.skydimension.forge;

import coolclk.skydimension.SkyDimension;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SkyDimension.MOD_ID)
public class ForgeMod {
    public static Logger LOGGER = LogManager.getLogger();
    public static MinecraftServer SERVER;

    public ForgeMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStarted);

        LOGGER.info("========== [Sky Dimension] ==========");
        LOGGER.info("The mod successfully launched!");
        LOGGER.info("Author: CoolCLK");
        LOGGER.info("Thanks for your using!");
        LOGGER.info("=====================================");
    }

    public void onServerStarted(FMLServerStartedEvent event) {
        SERVER = event.getServer();
    }
}