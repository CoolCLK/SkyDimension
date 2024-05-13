package coolclk.skydimension.forge.world.teleporter;

import net.minecraft.entity.Entity;
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
        new Teleporter(world.provider.getSpawnCoordinate()).placeEntity(world, entity, yaw);
    }
}
