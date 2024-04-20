package coolclk.skydimension.forge.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.potion.Potions;
import coolclk.skydimension.forge.world.dimension.DimensionSky;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static coolclk.skydimension.forge.ForgeMod.LOGGER;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class EventHandler {
    public static void beforeFMLInitialization() {
        registryDimension();
    }

    public static void onServerStarting(FMLServerStartingEvent event) {
        registryCommand(event);
    }

    @SubscribeEvent
    public static void onRegisterPotion(net.minecraftforge.event.RegistryEvent.Register<Potion> event) {
        Potions.registerPotions(event.getRegistry());
    }

    private static void registryDimension() {
        LOGGER.debug("Registering dimension(s)...");
        DimensionSky.registry();
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

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.dimension == DimensionSky.getDimensionId()) {
            player.addPotionEffect(new PotionEffect(Potions.SLOW_FALLING, 1, 0));
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1, 1));

            if (player.getPosition().getY() <= 0) {
                player.changeDimension(DimensionType.OVERWORLD.getId(), (world, entity, yaw) -> {
                    BlockPos pos = world.getTopSolidOrLiquidBlock(entity.getPosition());
                    entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUpEvent(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        int r = new Random().nextInt(100);
        int n = 75;
        if (r >= n) {
            DimensionSky.letPlayerGoDimension(player);
        }
    }

    @SubscribeEvent
    public static void onVillageSiege(VillageSiegeEvent event) {
        if (event.getWorld().provider.getDimensionType() == DimensionSky.getDimensionType()) {
            event.setCanceled(true);
        }
    }
}
