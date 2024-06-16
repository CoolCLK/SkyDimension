package coolclk.skydimension.forge.world.dimension;

import coolclk.skydimension.forge.world.biome.provider.BiomeProviderType;
import coolclk.skydimension.forge.world.biome.provider.SkyBiomeProviderSettings;
import coolclk.skydimension.forge.world.gen.ChunkGeneratorType;
import coolclk.skydimension.forge.world.gen.SkyGenerationSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The dimension class of sky.
 * @author CoolCLK
 */
public class SkyDimension extends Dimension {
    public SkyDimension(World world, DimensionType type) {
        super(world, type);
        this.setAllowedSpawnTypes(false, true);
    }

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
    public ChunkGenerator<?> createChunkGenerator() {
        SkyGenerationSettings generationSettings = ChunkGeneratorType.SKY.createSettings();
        SkyBiomeProviderSettings biomeProviderSettings = BiomeProviderType.SKY.createSettings();
        biomeProviderSettings.setGeneratorSettings(generationSettings);
        biomeProviderSettings.setWorldInfo(this.world.getWorldInfo());
        return ChunkGeneratorType.SKY.create(this.world, BiomeProviderType.SKY.create(biomeProviderSettings), generationSettings);
    }

    /**
     * No moon here.
     * @author CoolCLK
     */
    @Override
    public int getMoonPhase(long worldTime) {
        return 5;
    }

    /**
     * Send back player back to overworld.
     * @author CoolCLK
     */
    @Override
    public boolean canRespawnHere() {
        return false;
    }

    /**
     * Force set player spawn dimension to overworld if player respawn dimension is in sky dimension.
     *
     * @author CoolCLK
     */
    @Override
    public DimensionType getRespawnDimension(@Nonnull ServerPlayerEntity player) {
        DimensionType dimension = super.getRespawnDimension(player);
        if (dimension == this.getType()) {
            dimension = DimensionType.OVERWORLD;
        }
        return dimension;
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
    @OnlyIn(Dist.CLIENT)
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        int i = 0x8080a0;
        float f2 = MathHelper.cos(celestialAngle * 3.141593F * 2.0F) * 2.0F + 0.5F;
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }
        if (f2 > 1.0F) {
            f2 = 1.0F;
        }
        float r = (float) (i >> 16 & 0xff) / 255F;
        float g = (float) (i >> 8 & 0xff) / 255F;
        float b = (float) (i & 0xff) / 255F;
        r *= f2 * 0.94F + 0.06F;
        g *= f2 * 0.94F + 0.06F;
        b *= f2 * 0.91F + 0.09F;
        return new Vec3d(r, g, b);
    }

    /**
     * Change sky color.
     * @author CoolCLK
     */
    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public Vec3d getSkyColor(BlockPos cameraPos, float partialTicks) {
        return new Vec3d(186 / 255F,184 / 255F,242 / 255F);
    }

    /**
     * Make void lower.
     * @author CoolCLK
     */
    @Override
    public double getHorizon() {
        return Double.MIN_VALUE;
    }

    /**
     * Change the void factor.
     * @author CoolCLK
     */
    @Override
    public double getVoidFogYFactor() {
        return 8F;
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
     * Get a coordinate for player to spawn, or else create a platform.
     * @author CoolCLK
     */
    @Override
    @SuppressWarnings("ReassignedVariable")
    public BlockPos getSpawnCoordinate() {
        BlockPos coordinate = new BlockPos(0.5, 128, 0.5);
        BlockState ground = Blocks.OBSIDIAN.getDefaultState();
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
    @OnlyIn(Dist.CLIENT)
    public boolean isSkyColored() {
        return true;
    }

    /**
     * Change height of dimension.
     * @author CoolCLK
     */
    @Override
    public int getHeight() {
        return 256;
    }

    /**
     * Let it same as {@link #getHeight()}.
     * @author CoolCLK
     */
    @Override
    public int getActualHeight() {
        return this.getHeight();
    }


    @Nullable
    public BlockPos findSpawn(ChunkPos chunkCoordinate, boolean force) {
        for(int x = chunkCoordinate.getXStart(); x <= chunkCoordinate.getXEnd(); ++x) {
            for(int z = chunkCoordinate.getZStart(); z <= chunkCoordinate.getZEnd(); ++z) {
                BlockPos blockpos = this.findSpawn(x, z, force);
                if (blockpos != null) {
                    return blockpos;
                }
            }
        }

        return null;
    }

    @Nullable
    public BlockPos findSpawn(int x, int z, boolean force) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(x, 0, z);
        Biome biome = this.world.getBiome(blockpos$mutableblockpos);
        BlockState topBlock = biome.getSurfaceBuilderConfig().getTop();
        if (force && !topBlock.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            Chunk chunk = this.world.getChunk(x >> 4, z >> 4);
            int topY = chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
            if (topY < 0) {
                return null;
            } else if (chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15) > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, x & 15, z & 15)) {
                return null;
            } else {
                for(int y = topY + 1; y >= 0; --y) {
                    blockpos$mutableblockpos.setPos(x, y, z);
                    BlockState blockState = this.world.getBlockState(blockpos$mutableblockpos);
                    if (!blockState.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockState.equals(topBlock)) {
                        return blockpos$mutableblockpos.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public boolean doesXZShowFog(int chunkX, int chunkZ) {
        return false;
    }
}
