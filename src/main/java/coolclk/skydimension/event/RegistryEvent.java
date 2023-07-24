package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static coolclk.skydimension.SkyDimension.LOGGER;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RegistryEvent {
    @EventHandler
    public static void beforeFMLPreInitializationEvent(FMLPreInitializationEvent event) {
        LOGGER.debug("Start pre-initialization.");
        registryDimension();
    }

    @EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        registryCommand(true, event);
    }

    public static void registryDimension() {
        LOGGER.debug("Registering dimension(s)...");
        DimensionManager.registerDimension(DimensionSky.getDimensionId(), DimensionSky.getDimensionType());
    }

    public static void registryCommand(boolean isServer, FMLServerStartingEvent serverEvent) {
        LOGGER.debug("Registering command(s)...");
        List<ICommand> commands = Collections.singletonList(new CommandBase() {
            @Nonnull
            @Override
            public String getName() {
                return "go-sky";
            }

            @Nonnull
            @Override
            public String getUsage(@Nonnull ICommandSender sender) {
                return new TextComponentTranslation("command.skydimension.go-sky.usage").getFormattedText();
            }

            @Override
            public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
                EntityPlayer player = null;
                if (args.length > 0) {
                    player = server.getPlayerList().getPlayerByUsername(args[0]);
                } else if (sender instanceof EntityPlayer) {
                    player = (EntityPlayer) sender;
                }
                if (player != null) {
                    DimensionSky.letPlayerGoDimension(player);
                }
            }
        });
        commands.forEach(command -> {
            if (isServer) {
                serverEvent.registerServerCommand(command);
                return;
            }
            ClientCommandHandler.instance.registerCommand(command);
        });
    }
}
