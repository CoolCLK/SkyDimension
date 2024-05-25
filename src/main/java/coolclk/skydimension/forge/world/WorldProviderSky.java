package coolclk.skydimension.forge.world;

import coolclk.skydimension.forge.world.gen.ChunkGeneratorSky;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * The world provider of sky dimension.
 * @author CoolCLK
 */
public class WorldProviderSky extends WorldProvider {
    /**
     * Make the world full-bright.
     * @author CoolCLK
     */
    @Override
    protected void generateLightBrightnessTable() {
        for (int i = 0; i <= 15; i++) {
            float f = 0.5F + i / 10F;
            if (f > 1) {
                f = 1;
            }
            this.lightBrightnessTable[i] = f;
        }
    }

    /**
     * Create a chunk generator.
     * @author CoolCLK
     */
    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorSky(this.world);
    }

    /**
     * Lock the sky angle.
     * @author CoolCLK
     */
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks){
        return 0F;
    }

    /**
     * Get the color of fog.
     * @author CoolCLK
     */
    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public Vec3d getFogColor(float x, float z) {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(x * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }
        float f3 = (float) (i >> 16 & 0xff) / 255F;
        float f4 = (float) (i >> 8 & 0xff) / 255F;
        float f5 = (float) (i & 0xff) / 255F;
        f3 *= f2 * 0.94F + 0.06F;
        f4 *= f2 * 0.94F + 0.06F;
        f5 *= f2 * 0.91F + 0.09F;
        return new Vec3d(f3, f4, f5);
    }

    /**
     * Fix dark void.
     * @author CoolCLK
     */
    @Override
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
        return 0.0F;
    }

    /**
     * Switch the result of sleep.
     * @author CoolCLK
     */
    @Override
    @Nonnull
    public WorldSleepResult canSleepAt(@Nonnull EntityPlayer player, @Nonnull BlockPos pos) {
        return WorldSleepResult.ALLOW;
    }

    /**
     * Get the height of cloud.
     * @author CoolCLK
     */
    @Override
    public float getCloudHeight() {
        return 8F;
    }

    /**
     * Get the type of the dimension.
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public DimensionType getDimensionType() {
        return coolclk.skydimension.forge.world.DimensionType.SKY;
    }

    /**
     * Get the height of cloud.
     * @author CoolCLK
     */
    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        if (world.isChunkGeneratedAt(x, z)) {
            return false;
        }
        IBlockState k = world.getBlockState(world.getTopSolidOrLiquidBlock(new BlockPos(x, 128, z)));
        if (k.getBlock() == Blocks.AIR) {
            return false;
        } else {
            return k.getMaterial().isSolid();
        }
    }

    /**
     * Get a coordinate for player to spawn, or else create a platform.
     * @author CoolCLK
     */
    @Override
    public BlockPos getSpawnCoordinate() {
        BlockPos coordinate = super.getSpawnCoordinate();
        if (coordinate == null) {
            coordinate = new BlockPos(0, 128, 0);
            IBlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = -1; i < 3; i++) {
                this.world.setBlockState(coordinate.add(0, i, 0).west().north(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).west(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).west().south(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).north(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).south(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).east().north(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).east(), ground);
                this.world.setBlockState(coordinate.add(0, i, 0).east().south(), ground);
                ground = Blocks.AIR.getDefaultState();
            }
        }
        return coordinate;
    }

    /**
     * Lock weather.
     * @author CoolCLK
     */
    @Override
    public boolean canDoLightning(@Nonnull Chunk chunk) {
        return false;
    }

    /**
     * Lock weather.
     * @author CoolCLK
     */
    @Override
    public boolean canDoRainSnowIce(@Nonnull Chunk chunk) {
        return false;
    }

    /**
     * Lock sky color state.
     * @author CoolCLK
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored() {
        return true;
    }
}
