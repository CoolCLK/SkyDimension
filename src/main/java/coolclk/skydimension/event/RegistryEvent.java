package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static coolclk.skydimension.SkyDimension.LOGGER;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RegistryEvent {
    @Mod.EventHandler
    public static void beforeFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        LOGGER.debug("Start pre-initialization.");
        registryDimension();
    }

    public static void registryDimension() {
        LOGGER.debug("Registering dimension(s)...");
        DimensionManager.registerDimension(DimensionSky.getDimensionId(), DimensionSky.getDimensionType());
    }
}
