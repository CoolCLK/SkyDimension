package coolclk.skydimension.forge.event;

import coolclk.skydimension.forge.ForgeMod;
import coolclk.skydimension.forge.world.World;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Teleporter;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (player.level == Objects.requireNonNull(ForgeMod.SERVER).getLevel(World.SKY)) {
            player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 1, 0));
            player.addEffect(new EffectInstance(Effects.JUMP, 1, 1));

            if (player.position().y() <= 0) {
                ServerWorld overworld = Objects.requireNonNull(ForgeMod.SERVER.getLevel(ServerWorld.OVERWORLD));
                player.changeDimension(overworld, new Teleporter(overworld));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUpEvent(PlayerWakeUpEvent event) {
        PlayerEntity player = event.getPlayer();
        int r = new Random().nextInt(100);
        int n = 75;
        if (r >= n) {
            player.changeDimension(Objects.requireNonNull(ForgeMod.SERVER.getLevel(World.SKY)));
        }
    }

    @SubscribeEvent
    public static void onVillageSiege(VillageSiegeEvent event) {
        if (event.getWorld().dimensionType() == Objects.requireNonNull(ForgeMod.SERVER.getLevel(World.SKY)).dimensionType()) {
            event.setCanceled(true);
        }
    }
}
