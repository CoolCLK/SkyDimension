package coolclk.skydimension.forge.world.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Involve the function {@link net.minecraft.world.WorldProvider#getSpawnCoordinate()} and put player to the result.
 * @author CoolCLK
 */
public class SpawnTeleporter extends Teleporter {
    public SpawnTeleporter() {
        super(null);
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        BlockPos pos = world.provider.getSpawnCoordinate();
        if (pos == null) pos = world.provider.getSpawnPoint();
        new Teleporter(pos).placeEntity(world, entity, yaw);
    }
}
