package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class PlayerEvent {
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
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.getPosition().getY() <= 0 && player.dimension == DimensionSky.getDimensionId()) {
            player.changeDimension(DimensionType.OVERWORLD.getId());
            player.setPosition(player.world.getSpawnPoint().getX(), player.world.getSpawnPoint().getY(), player.world.getSpawnPoint().getZ());
        }
    }
}
