package coolclk.skydimension.forge.world.provider;

import coolclk.skydimension.forge.world.dimension.DimensionSky;
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

import static coolclk.skydimension.forge.ForgeMod.LOGGER;

public class WorldProviderSky extends WorldProvider {
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

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new ChunkProviderSky(world, world.getSeed());
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks)
    {
        return 0F;
    }

    @Override
    public float[] calcSunriseSunsetColors(float f, float f1) {
        return null;
    }

    @Override
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

    @Override
    @Nonnull
    public WorldSleepResult canSleepAt(@Nonnull EntityPlayer player, @Nonnull BlockPos pos) {
        return WorldSleepResult.ALLOW;
    }

    @Override
    public float getCloudHeight() {
        return 8F;
    }

    @Nonnull
    @Override
    public DimensionType getDimensionType() {
        return DimensionSky.getDimensionType();
    }

    @Override
    public boolean canCoordinateBeSpawn(int x, int z) {
        if (world.isChunkGeneratedAt(x, z)) {
            return false;
        }
        IBlockState k = world.getBlockState(world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)));
        if (k.getBlock() == Blocks.AIR) {
            return false;
        } else {
            return k.getMaterial().isSolid();
        }
    }

    @Override
    public BlockPos getSpawnCoordinate() {
        BlockPos spawnCoordinate = super.getSpawnCoordinate();
        if (spawnCoordinate == null) {
            int skip = 8;
            int range = 256;
            for (int triedX = -range; triedX <= range; triedX += skip) {
                for (int triedZ = -range; triedZ <= range; triedZ += skip) {
                    if (canCoordinateBeSpawn(triedX, triedZ)) {
                        spawnCoordinate = world.getTopSolidOrLiquidBlock(new BlockPos(triedX, 0, triedZ));
                        LOGGER.debug("Spawn Coordinate found: (x: " + triedX + ", z: " + triedZ + ")");
                        return spawnCoordinate;
                    }
                }
            }
        }
        LOGGER.error("Spawn Coordinate found fail.");
        return spawnCoordinate;
    }

    @Override
    public boolean canDoLightning(@Nonnull Chunk chunk) {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(@Nonnull Chunk chunk) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSkyColored()
    {
        return true;
    }
}
