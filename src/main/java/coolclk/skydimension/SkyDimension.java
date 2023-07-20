package coolclk.skydimension;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = SkyDimension.MOD_ID)
public class SkyDimension {
    public final static String MOD_ID = "skydimension";

    public static Logger LOGGER;

    @Mod.EventHandler
    public static void onFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
    }
}