package coolclk.skydimension.forge;

import coolclk.skydimension.ModLoader;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.common.DimensionManager;
import coolclk.skydimension.forge.event.EventHandler;
import coolclk.skydimension.forge.tileentity.TileEntity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Entrance of the mod in Forge.
 * @author CoolCLK
 */
@Mod(modid = SkyDimension.MOD_ID)
public class ForgeMod {
    @Mod.EventHandler
    public static void beforeFMLInitialization(FMLPreInitializationEvent event) {
        new DimensionManager();
        new TileEntity();
    }

    @Mod.EventHandler
    public static void onFMLInitialization(FMLInitializationEvent event) {
        SkyDimension.MOD_LOADER = ModLoader.FORGE;
    }

    @Mod.EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        EventHandler.onServerStarting(event);
    }
}