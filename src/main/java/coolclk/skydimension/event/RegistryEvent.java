package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RegistryEvent {
    @Mod.EventHandler
    public static void beforeFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        registryDimension();
    }

    public static void registryDimension() {
        while (DimensionSky.getType() == null) {}
        DimensionManager.registerDimension(DimensionSky.getId(), DimensionSky.getType());
    }
}
