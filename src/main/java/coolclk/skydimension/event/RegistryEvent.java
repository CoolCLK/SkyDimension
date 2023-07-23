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
        registryDimension();
        LOGGER.debug("Start pre-initialization.");
    }

    public static void registryDimension() {
        DimensionManager.registerDimension(DimensionSky.getDimensionId(), DimensionSky.getDimensionType());
        LOGGER.debug("Registering dimension " + DimensionSky.getDimensionName() + " (id: " + DimensionSky.getDimensionId() + ").");
    }
}
