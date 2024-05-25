package coolclk.skydimension.forge.block;

import coolclk.skydimension.forge.tileentity.TileEntitySkyPortal;
import coolclk.skydimension.forge.world.DimensionType;
import coolclk.skydimension.forge.world.teleporter.SpawnTeleporter;
import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * The portal of dimension sky.
 * @author CoolCLK7065
 */
public class BlockSkyPortal extends BlockEndPortal {
    public BlockSkyPortal(Material material) {
        super(material);
    }

    /**
     * Change the tile to sky portal's tile.
     * @author CoolCLK7065
     */
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEntitySkyPortal();
    }

    /**
     * Change dimension to sky.
     * @author CoolCLK7065
     */
    @Override
    public void onEntityCollidedWithBlock(World world, @Nonnull BlockPos pos, @Nonnull IBlockState blockState, @Nonnull Entity entity) {
        if (!world.isRemote && !entity.isRiding() && !entity.isBeingRidden() && entity.isNonBoss() && entity.getEntityBoundingBox().intersects(blockState.getBoundingBox(world, pos).offset(pos))) {
            entity.changeDimension(entity.dimension == DimensionType.SKY.getId() ? net.minecraft.world.DimensionType.OVERWORLD.getId() : DimensionType.SKY.getId(), new SpawnTeleporter());
        }
    }

    /**
     * Just a map color.
     * @author CoolCLK7065
     */
    @Nonnull
    @Override
    public MapColor getMapColor(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos) {
        return MapColor.PINK;
    }
}
