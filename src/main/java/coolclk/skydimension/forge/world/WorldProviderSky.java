package coolclk.skydimension.forge.world;

import coolclk.skydimension.forge.world.gen.ChunkGeneratorSky;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * The world provider of sky dimension.
 * @author CoolCLK
 */
public class WorldProviderSky extends WorldProvider {
    /**
     * Init provider.
     * @author CoolCLK
     */
    @Override
    public void init() {
        super.init();
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
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorSky(this.world);
    }

    /**
     * Send back player back to overworld.
     * @author CoolCLK
     */
    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    /**
     * Force set player spawn dimension to overworld if player respawn dimension is in sky dimension.
     * @author CoolCLK
     */
    @Override
    public int getRespawnDimension(@Nonnull EntityPlayerMP player) {
        int dimension = super.getRespawnDimension(player);
        if (dimension == this.getDimension()) {
            dimension = net.minecraft.world.DimensionType.OVERWORLD.getId();
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
    @SideOnly(Side.CLIENT)
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
    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public Vec3d getSkyColor(@Nullable Entity cameraEntity, float partialTicks) {
        return new Vec3d(186 / 255F,184 / 255F,242 / 255F);
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
     * Get the type of the dimension.
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return DimensionType.SKY;
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
    @SuppressWarnings("ReassignedVariable")
    public BlockPos getSpawnCoordinate() {
        BlockPos coordinate = new BlockPos(0, 128, 0);
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

    /**
     * Ready for higher version of Minecraft.
     * @author CoolCLK
     */
    public int getBaseHeight() {
        return 0;
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
}
