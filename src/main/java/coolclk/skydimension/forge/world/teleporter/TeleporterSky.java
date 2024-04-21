package coolclk.skydimension.forge.world.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class TeleporterSky implements ITeleporter {
    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        BlockPos spawnPos = currentWorld.getSharedSpawnPos();
        entity.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        return
    }
}
