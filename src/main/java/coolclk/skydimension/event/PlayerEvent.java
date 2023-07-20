package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

import static coolclk.skydimension.SkyDimension.LOGGER;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class PlayerEvent {
    @SubscribeEvent
    public static void onPlayerSleepInBed(PlayerWakeUpEvent event) {
        LOGGER.debug("Player woke up.");
        EntityPlayer player = event.getEntityPlayer();
        if (new Random().nextInt(100) > 80) {
            DimensionSky.go(player);
            player.sendMessage(new TextComponentTranslation("Where am I?"));
        }
    }
}
