package coolclk.skydimension.forge.event;

import com.mojang.brigadier.Command;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.potion.Potions;
import coolclk.skydimension.forge.world.dimension.DimensionSky;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

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

    private static void registryDimension() {
        LOGGER.debug("Registering dimension(s)...");
        DimensionSky.registry();
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
