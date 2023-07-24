package coolclk.skydimension.world.provider;

import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class WorldProviderSky extends WorldProvider {
    private final static int getSpawnCoordinateMaxRange = 128;
    public WorldProviderSky() {
        this.nether = false;
    }

    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderSky(world, world.getSeed());
    }

    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0.0F;
    }

    public float[] calcSunriseSunsetColors(float f, float f1) {
        return null;
    }

    @Nonnull
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

    public boolean canRespawnHere() {
        return false;
    }

    public float getCloudHeight() {
        return 8F;
    }

    @Nonnull
    @Override
    public DimensionType getDimensionType() {
        return DimensionSky.getDimensionType();
    }

    public boolean canCoordinateBeSpawn(int x, int z) {
        IBlockState k = world.getBlockState(world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)));
        if (k.getBlock() == Blocks.AIR) {
            return false;
        } else {
            return k.getMaterial().isSolid();
        }
    }

    @Nullable
    public BlockPos getSpawnCoordinate() {
        BlockPos spawnCoordinate = null;
        for (int r = 0; r <= 360; r++) {
            BlockPos tryPos = new BlockPos(Math.cos(r) * getSpawnCoordinateMaxRange, 0, Math.sin(r) * getSpawnCoordinateMaxRange);
            IBlockState tryState = world.getBlockState(world.getTopSolidOrLiquidBlock(tryPos));
            if (tryState.getBlock() != Blocks.AIR && tryState.getMaterial().isSolid()) {
                break;
            }
        }
        return spawnCoordinate;
    }
}
