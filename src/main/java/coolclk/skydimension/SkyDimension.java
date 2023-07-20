package coolclk.skydimension;

import coolclk.skydimension.event.PlayerEvent;
import coolclk.skydimension.event.RegistryEvent;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
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

        LOGGER.info("========== [Sky Dimension] ==========");
        LOGGER.info("The mod successfully launched!       ");
        LOGGER.info("Author: CoolCLK                      ");
        LOGGER.info("Thanks for your using!               ");
        LOGGER.info("=====================================");

        ClientCommandHandler.instance.registerCommand(new CommandBase() {
            @Override
            public String getName() {
                return "sky";
            }

            @Override
            public String getUsage(ICommandSender iCommandSender) {
                return "Debug only.";
            }

            @Override
            public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
                if (iCommandSender instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) iCommandSender;
                    DimensionSky.go(player);
                }
            }

            @Override
            public int getRequiredPermissionLevel() {
                return 0;
            }
        });
    }
}