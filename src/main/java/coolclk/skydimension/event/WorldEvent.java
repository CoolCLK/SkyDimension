package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.init.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (entity.isPotionActive(Potions.SLOW_FALLING)) {
            entity.fallDistance = 0;
        }
    }
}
