package coolclk.skydimension.forge.world.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

public class Teleporter implements ITeleporter {
    protected double x, y, z;

    public Teleporter(BlockPos position) {
        this(position != null ? position.getX() : 0, position != null ? position.getY() : 0, position != null ? position.getZ() : 0);
    }

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
