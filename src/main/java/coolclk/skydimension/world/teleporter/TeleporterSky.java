package coolclk.skydimension.world.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class TeleporterSky implements ITeleporter {
    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        BlockPos spawnPos = world.provider.getSpawnCoordinate();
        if (spawnPos == null) {
            spawnPos = new BlockPos(8, 8, 8);
        }
        entity.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
    }
}
