package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.api.ProjectE;
import coolclk.skydimension.init.Blocks;
import coolclk.skydimension.init.Items;
import coolclk.skydimension.init.Potions;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static coolclk.skydimension.SkyDimension.LOGGER;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class RegistryEvent {
    public static void beforeFMLPreInitialization() {
        registryDimension();
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        registryCommand(event);
        ProjectE.registerEMC(event.getServer());
    }

    @SubscribeEvent
    public static void onRegisterBlock(net.minecraftforge.event.RegistryEvent.Register<Block> event) {
        Blocks.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onRegisterItem(net.minecraftforge.event.RegistryEvent.Register<Item> event) {
        Items.registerItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void onRegisterPotion(net.minecraftforge.event.RegistryEvent.Register<Potion> event) {
        Potions.registerPotions(event.getRegistry());
    }

    private static void registryDimension() {
        LOGGER.debug("Registering dimension(s)...");
        DimensionSky.registry();
    }

    public static void registrySmelting() {
        LOGGER.debug("Registering recipe(s)...");
        GameRegistry.addSmelting(Block.getBlockFromName("sky_ore"), new ItemStack(Items.SKY_INGOT), 5.0F);
        GameRegistry.addSmelting(Item.getByNameOrId("ice_coal"), new ItemStack(net.minecraft.init.Items.COAL), 1.0F);
        GameRegistry.addSmelting(Block.getBlockFromName("ice_coal_block"), new ItemStack(net.minecraft.init.Blocks.COAL_BLOCK), 1.0F);
    }

    private static void registryCommand(@Nullable FMLServerStartingEvent serverEvent) {
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
            if (serverEvent != null) {
                serverEvent.registerServerCommand(command);
                return;
            }
            ClientCommandHandler.instance.registerCommand(command);
        });
    }
}
