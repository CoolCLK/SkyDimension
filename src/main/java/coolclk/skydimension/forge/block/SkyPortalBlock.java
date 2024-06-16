package coolclk.skydimension.forge.block;

import coolclk.skydimension.forge.tileentity.SkyPortalTileEntity;
import coolclk.skydimension.forge.util.EntityHelper;
import coolclk.skydimension.forge.world.DimensionType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;

import javax.annotation.Nonnull;

/**
 * The portal of dimension sky.
 * @author CoolCLK
 */
public class SkyPortalBlock extends EndPortalBlock {
    protected SkyPortalBlock(Block.Properties properties) {
        super(properties);
    }

    /**
     * Change the tile to sky portal's tile.
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader blockReader) {
        return new SkyPortalTileEntity();
    }

    /**
     * Change dimension to sky.
     * @author CoolCLK
     */
    @Override
    public void onEntityCollision(@Nonnull BlockState blockState, World world, @Nonnull BlockPos position, @Nonnull Entity entity) {
        if (!world.isRemote && !entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss() && VoxelShapes.compare(VoxelShapes.create(entity.getBoundingBox().offset(-position.getX(), -position.getY(), -position.getZ())), blockState.getShape(world, position), IBooleanFunction.AND)) {
            EntityHelper.teleportEntityToDimension(entity, world.dimension.getType() == DimensionType.SKY ? net.minecraft.world.dimension.DimensionType.OVERWORLD : DimensionType.SKY, (serverWorld) -> world.dimension.getType() == DimensionType.SKY ? serverWorld.getSpawnCoordinate() : serverWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, serverWorld.getSpawnPoint()));
        }
    }
}
