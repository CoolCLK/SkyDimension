package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Blocks;
import coolclk.skydimension.init.Items;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static coolclk.skydimension.SkyDimension.LOGGER;

@EventBusSubscriber
public class RegistryEvent {
    @EventHandler
    public static void beforeFMLPreInitialization(FMLPreInitializationEvent event) {
        registryDimension();
    }

    @EventHandler
    public static void onServerStarting(FMLServerStartingEvent event) {
        registryCommand(true, event);
    }

    @SubscribeEvent
    public static void onRegisterBlock(net.minecraftforge.event.RegistryEvent.Register<Block> event) {
        event.getRegistry().register(
                (Blocks.SKY_ORE = (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setUnlocalizedName("oreSky").setRegistryName(SkyDimension.MOD_ID, "sky_ore"))
        );
    }

    @SubscribeEvent
    public static void onRegisterItem(net.minecraftforge.event.RegistryEvent.Register<Item> event) {
        event.getRegistry().register(
                (Items.SKY_ORE = (ItemBlock) (new ItemBlock(Blocks.SKY_ORE)).setRegistryName(SkyDimension.MOD_ID, "sky_ore"))
        );
    }

    public static void registryDimension() {
        LOGGER.info("Registering dimension(s)...");
        DimensionSky.registry();
    }

    public static void registryCommand(boolean isServer, FMLServerStartingEvent serverEvent) {
        LOGGER.info("Registering command(s)...");
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
