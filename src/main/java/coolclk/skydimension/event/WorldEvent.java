package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void onLivingFall(TickEvent.WorldTickEvent event) {
        for (EntityLivingBase entity : event.world.getEntities(EntityLivingBase.class, input -> true)) {
            if (entity.isPotionActive(Potions.SLOW_FALLING)) {
                if (!entity.onGround) {
                    entity.setNoGravity(true);
                    entity.fallDistance = 0;
                    entity.setNoGravity(false);
                }
            }
        }
    }
}
