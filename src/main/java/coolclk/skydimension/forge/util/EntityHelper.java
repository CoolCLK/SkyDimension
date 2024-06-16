package coolclk.skydimension.forge.util;

import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class EntityHelper {
    public interface ServerWorldSpawnCoordinate {
        BlockPos get(ServerWorld world);
    }

    @SuppressWarnings({"UnusedReturnValue", "SameReturnValue", "deprecation", "DataFlowIssue"})
    public static Entity teleportEntityToDimension(Entity entity, DimensionType dimensionType, ServerWorldSpawnCoordinate spawnCoordinate) {
        if (!entity.world.isRemote && !entity.removed) {
            entity.world.getProfiler().startSection("changeDimension");
            MinecraftServer server = entity.getServer();
            ServerWorld entityServerWorld = server.getWorld(entity.dimension);
            ServerWorld targetServerWorld = server.getWorld(dimensionType);
            entity.dimension = dimensionType;
            entity.detach();
            entity.world.getProfiler().startSection("reposition");
            Vec3d entityMotion = entity.getMotion();
            BlockPos blockpos = spawnCoordinate.get(targetServerWorld);

            entity.world.getProfiler().endStartSection("reloading");
            Entity antherDimensionEntity = entity.getType().create(targetServerWorld);
            if (antherDimensionEntity != null) {
                antherDimensionEntity.copyDataFromOld(entity);
                antherDimensionEntity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
                antherDimensionEntity.setMotion(entityMotion);
                targetServerWorld.func_217460_e(antherDimensionEntity);
            }

            entity.remove(false);
            entity.world.getProfiler().endSection();
            entityServerWorld.resetUpdateEntityTick();
            targetServerWorld.resetUpdateEntityTick();
            entity.world.getProfiler().endSection();
            return antherDimensionEntity;
        } else {
            return null;
        }
    }
}
