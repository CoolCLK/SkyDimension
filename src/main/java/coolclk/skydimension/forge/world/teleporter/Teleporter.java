package coolclk.skydimension.forge.world.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

/**
 * Simple teleporter with only function which teleport player.
 * @author CoolCLK
 */
public class Teleporter implements ITeleporter {
    protected double x, y, z;

    /**
     * Create a simple teleporter.
     * @param position target position
     * @author CoolCLK
     */
    public Teleporter(BlockPos position) {
        this(position != null ? position.getX() : 0, position != null ? position.getY() : 0, position != null ? position.getZ() : 0);
    }

    /**
     * Create a simple teleporter.
     * @param x target position x
     * @param y target position y
     * @param z target position z
     * @author CoolCLK
     */
    public Teleporter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        entity.setPosition(x, y, z);
    }
}
