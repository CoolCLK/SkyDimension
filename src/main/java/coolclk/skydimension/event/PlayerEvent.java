package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static coolclk.skydimension.SkyDimension.LOGGER;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class PlayerEvent {
    @SubscribeEvent
    public static void onPlayerWakeUpEvent(PlayerWakeUpEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        int r = new Random().nextInt(100);
        int n = 75;
        LOGGER.debug("Player woke up. sky per: " + r + "/" + n);
        if (r >= n) {
            DimensionSky.go(player);
        }
    }
}
