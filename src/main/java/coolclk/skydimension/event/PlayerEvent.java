package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static coolclk.skydimension.SkyDimension.LOGGER;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class PlayerEvent {
    @SubscribeEvent
    public static void onPlayerSleepInBed(PlayerSleepInBedEvent event) {
        if (event.getResultStatus() == EntityPlayer.SleepResult.OK) {
            LOGGER.debug("Player slept.");
            if (new Random().nextBoolean()) {
                EntityPlayer player = event.getEntityPlayer();
                player.wakeUpPlayer(true, false, true);
                player.setWorld(DimensionSky.getWorld());
                LOGGER.debug("Sending player to dimension.");
            }
        }
    }
}
