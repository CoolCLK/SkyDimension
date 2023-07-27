package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Items;
import coolclk.skydimension.init.Potions;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class PlayerEvent {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.dimension == DimensionSky.getDimensionId()) {
            player.addPotionEffect(new PotionEffect(Potions.SLOW_FALLING, 1, 1));
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 1, 2));

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
}
