package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import coolclk.skydimension.world.provider.WorldProviderSky;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RegistryEvent {
    @Mod.EventHandler
    public void onFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        registryDimension();
    }

    public static void registryDimension() {
        DimensionSky.setType(DimensionType.register(DimensionSky.getName(), DimensionSky.getSuffix(), DimensionSky.getId(), WorldProviderSky.class, false));
        DimensionManager.registerDimension(DimensionSky.getId(), DimensionSky.getType());
    }
}
