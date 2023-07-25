package coolclk.skydimension.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.world.DimensionType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class WorldEvent {
    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        event.world.getLoadedEntityList().stream().filter(e -> e.dimension == DimensionSky.getDimensionId() && e.getPosition().getY() <= 0).findAny().ifPresent(e -> e.changeDimension(DimensionType.OVERWORLD.getId(), (world, entity, yaw) -> {
            entity.fallDistance = 0;
            entity.setPosition(world.getSpawnPoint().getX(), world.getTopSolidOrLiquidBlock(world.getSpawnPoint()).getY(), world.getSpawnPoint().getZ());
        }));
    }
}
