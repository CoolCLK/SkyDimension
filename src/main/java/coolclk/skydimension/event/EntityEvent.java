package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.entity.Entity;
import net.minecraft.world.DimensionType;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class EntityEvent {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        Entity fallEntity = event.getEntity();
        if (fallEntity.getPosition().getY() <= 0 && fallEntity.dimension == DimensionSky.getDimensionId()) {
            fallEntity.changeDimension(DimensionType.OVERWORLD.getId(), (world, entity, yaw) -> {
                entity.fallDistance = 0;
                entity.setPosition(world.getSpawnPoint().getX(), world.getTopSolidOrLiquidBlock(world.getSpawnPoint()).getY(), world.getSpawnPoint().getZ());
            });
        }
    }
}
