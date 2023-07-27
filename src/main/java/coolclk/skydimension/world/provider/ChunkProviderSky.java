package coolclk.skydimension.world.provider;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ChunkProviderSky implements IChunkGenerator {
    public ChunkProviderSky(World world, long l) {
        field_28079_r = new double[256];
        field_28078_s = new double[256];
        field_28077_t = new double[256];
        caveGenerator = new MapGenCaves();

        this.world = world;
        seedRandomizer = new Random(l);
        field_28086_k = new NoiseGeneratorOctaves(seedRandomizer, 16);
        field_28085_l = new NoiseGeneratorOctaves(seedRandomizer, 16);
        field_28084_m = new NoiseGeneratorOctaves(seedRandomizer, 8);
        field_28083_n = new NoiseGeneratorOctaves(seedRandomizer, 4);
        field_28082_o = new NoiseGeneratorOctaves(seedRandomizer, 4);
        field_28096_a = new NoiseGeneratorOctaves(seedRandomizer, 10);
        field_28095_b = new NoiseGeneratorOctaves(seedRandomizer, 16);
        field_28094_c = new NoiseGeneratorOctaves(seedRandomizer, 8);
    }

    public void generateUnderground(int xOffset, int zOffset, byte[] bytes) {
        byte scale = 2;
        int xSize = scale + 1;
        byte ySize = 33;
        int zSize = scale + 1;
        field_28080_q = generateANoiseOctave(field_28080_q, xOffset * scale, zOffset * scale, xSize, ySize, zSize);
        for (int i1 = 0; i1 < scale; i1++) {
            for (int j1 = 0; j1 < scale; j1++) {
                for (int k1 = 0; k1 < 32; k1++) {
                    double d = 0.25D;
                    double d1 = field_28080_q[((i1) * zSize + (j1)) * ySize + (k1)];
                    double d2 = field_28080_q[((i1) * zSize + (j1 + 1)) * ySize + (k1)];
                    double d3 = field_28080_q[((i1 + 1) * zSize + (j1)) * ySize + (k1)];
                    double d4 = field_28080_q[((i1 + 1) * zSize + (j1 + 1)) * ySize + (k1)];
                    double d5 = (field_28080_q[((i1) * zSize + (j1)) * ySize + (k1 + 1)] - d1) * d;
                    double d6 = (field_28080_q[((i1) * zSize + (j1 + 1)) * ySize + (k1 + 1)] - d2) * d;
                    double d7 = (field_28080_q[((i1 + 1) * zSize + (j1)) * ySize + (k1 + 1)] - d3) * d;
                    double d8 = (field_28080_q[((i1 + 1) * zSize + (j1 + 1)) * ySize + (k1 + 1)] - d4) * d;
                    for (int l1 = 0; l1 < 4; l1++) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i2 = 0; i2 < 8; i2++) {
                            int j2 = i2 + i1 * 8 << 11 | j1 * 8 << 7 | k1 * 4 + l1;
                            char c = '\200';
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int k2 = 0; k2 < 8; k2++) {
                                int l2 = 0;
                                if (d15 > 0.0D) {
                                    l2 = BlockSand.getIdFromBlock(Blocks.STONE);
                                }
                                bytes[j2] = (byte)l2;
                                j2 += c;
                                d15 += d16;
                            }
                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void generateBiomes(int x, int z, byte[] bytes, Biome[] abiome) {
        double d = 0.03125D;
        field_28079_r = field_28083_n.generateNoiseOctaves(field_28079_r, x * 16, z * 16, 0, 16, 16, 1, d, d, 1.0D);
        field_28078_s = field_28083_n.generateNoiseOctaves(field_28078_s, x * 16, (int) 109.0134D, z * 16, 16, 1, 16, d, 1.0D, d);
        field_28077_t = field_28082_o.generateNoiseOctaves(field_28077_t, x * 16, z * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for (int gx = 0; gx < 16; gx++) {
            for (int gz = 0; gz < 16; gz++) {
                Biome biome = abiome[gx + gz * 16];
                int i1 = (int)(field_28077_t[gx + gz * 16] / 3D + 3D + seedRandomizer.nextDouble() * 0.25D);
                int j1 = -1;
                byte byte0 = (byte) BlockSand.getStateId(biome.topBlock);
                byte byte1 = (byte) BlockSand.getStateId(biome.fillerBlock);
                for (int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (gz * 16 + gx) * 128 + k1;
                    byte byte2 = bytes[l1];
                    if (byte2 == 0) {
                        j1 = -1;
                        continue;
                    }
                    if (byte2 != BlockSand.getIdFromBlock(Blocks.STONE)) {
                        continue;
                    }
                    if (j1 == -1) {
                        if (i1 <= 0) {
                            byte0 = 0;
                            byte1 = (byte) BlockSand.getIdFromBlock(Blocks.STONE);
                        }
                        j1 = i1;
                        bytes[l1] = byte0;

                        continue;
                    }
                    if (j1 <= 0) {
                        continue;
                    }
                    j1--;
                    bytes[l1] = byte1;
                    if (j1 == 0 && byte1 == BlockSand.getIdFromBlock(Blocks.SAND)) {
                        j1 = seedRandomizer.nextInt(4);
                        byte1 = (byte) BlockSand.getIdFromBlock(Blocks.SANDSTONE);
                    }
                }
            }
        }
    }

    public Chunk provideChunk(int x, int z) {
        seedRandomizer.setSeed((long) x * 0x4f9939f508L + (long) x * 0x1ef1565bd5L);
        byte[] bytes = new byte[32768];
        biomes = world.getBiomeProvider().getBiomes(biomes, x * 16, z * 16, 16, 16);
        generateUnderground(x, z, bytes);
        generateBiomes(x, z, bytes, biomes);
        Chunk chunk = bytesToChunk(world, bytes, x, z);
        caveGenerator.generate(world, x, z, bytesToChunkPrimer(bytes));
        chunk.generateSkylightMap();
        return chunk;
    }

    private double[] generateANoiseOctave(double[] ad, int xOffset, int zOffset, int xSize, int ySize, int zSize) {
        if (ad == null) {
            ad = new double[xSize * ySize * zSize];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        field_28090_g = field_28096_a.generateNoiseOctaves(field_28090_g, xOffset, zOffset, xSize, zSize, 1.121D, 1.121D, 0.5D);
        field_28089_h = field_28095_b.generateNoiseOctaves(field_28089_h, xOffset, zOffset, xSize, zSize, 200D, 200D, 0.5D);
        d *= 2D;
        field_28093_d = field_28084_m.generateNoiseOctaves(field_28093_d, xOffset, 0, zOffset, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
        field_28092_e = field_28086_k.generateNoiseOctaves(field_28092_e, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
        field_28091_f = field_28085_l.generateNoiseOctaves(field_28091_f, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
        int k1 = 0;
        for (int j2 = 0; j2 < xSize; j2++) {
            for (int l2 = 0; l2 < zSize; l2++) {
                for (int j3 = 0; j3 < ySize; j3++) {
                    double d8;
                    double d10 = field_28092_e[k1] / 512D;
                    double d11 = field_28091_f[k1] / 512D;
                    double d12 = (field_28093_d[k1] / 10D + 1.0D) / 2D;
                    if (d12 < 0.0D) {
                        d8 = d10;
                    } else if (d12 > 1.0D) {
                        d8 = d11;
                    } else {
                        d8 = d10 + (d11 - d10) * d12;
                    }
                    d8 -= 8D;
                    int k3 = 32;
                    if (j3 > ySize - k3) {
                        double d13 = (float) (j3 - (ySize - k3)) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if (j3 < k3) {
                        double d14 = (float) (k3 - j3) / ((float) k3 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }
                    ad[k1] = d8;
                    k1++;
                }
            }
        }
        return ad;
    }

    @Nonnull
    @Override
    public Chunk generateChunk(int x, int z) {
        return provideChunk(x, z);
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        BlockSand.fallInstantly = true;
        int x = chunkX * 16;
        int z = chunkZ * 16;
        Biome biome = world.getBiome(new BlockPos(x + 16, 0, z + 16));
        seedRandomizer.setSeed(world.getSeed());
        long l1 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        long l2 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        seedRandomizer.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ world.getSeed());
        double d;
        if (seedRandomizer.nextInt(4) == 0) {
            int i1 = x + seedRandomizer.nextInt(16) + 8;
            int l4 = seedRandomizer.nextInt(128);
            int i8 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(world, seedRandomizer, new BlockPos(i1, l4, i8));
        }
        if (seedRandomizer.nextInt(8) == 0) {
            int j1 = x + seedRandomizer.nextInt(16) + 8;
            int i5 = seedRandomizer.nextInt(seedRandomizer.nextInt(120) + 8);
            int j8 = z + seedRandomizer.nextInt(16) + 8;
            if (i5 < 64 || seedRandomizer.nextInt(10) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(world, seedRandomizer, new BlockPos(j1, i5, j8));
            }
        }
        for (int k1 = 0; k1 < 8; k1++) {
            int j5 = x + seedRandomizer.nextInt(16) + 8;
            int k8 = seedRandomizer.nextInt(128);
            int i13 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(world, seedRandomizer, new BlockPos(j5, k8, i13));
        }
        for (int i2 = 0; i2 < 10; i2++) {
            int k5 = x + seedRandomizer.nextInt(16);
            int l8 = seedRandomizer.nextInt(128);
            int j13 = z + seedRandomizer.nextInt(16);
            (new WorldGenClay(32)).generate(world, seedRandomizer, new BlockPos(k5, l8, j13));
        }
        for (int j2 = 0; j2 < 20; j2++) {
            int l5 = x + seedRandomizer.nextInt(16);
            int i9 = seedRandomizer.nextInt(128);
            int k13 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(l5, i9, k13));
        }
        for (int k2 = 0; k2 < 10; k2++) {
            int i6 = x + seedRandomizer.nextInt(16);
            int j9 = seedRandomizer.nextInt(128);
            int l13 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(i6, j9, l13));
        }
        for (int i3 = 0; i3 < 30; i3++) {
            int j6 = x + seedRandomizer.nextInt(16);
            int k9 = seedRandomizer.nextInt(128);
            int i14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(coolclk.skydimension.init.Blocks.ICE_COAL_ORE.getDefaultState(), 16)).generate(world, seedRandomizer, new BlockPos(j6, k9, i14));
        }
        for (int j3 = 0; j3 < 25; j3++) {
            int k6 = x + seedRandomizer.nextInt(16);
            int l9 = seedRandomizer.nextInt(64);
            int j14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(k6, l9, j14));
        }
        for (int k3 = 0; k3 < 3; k3++) {
            int l6 = x + seedRandomizer.nextInt(16);
            int i10 = seedRandomizer.nextInt(32);
            int k14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(l6, i10, k14));
        }
        for (int l3 = 0; l3 < 12; l3++) {
            int i7 = x + seedRandomizer.nextInt(16);
            int j10 = seedRandomizer.nextInt(16);
            int l14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(i7, j10, l14));
        }
        for (int i4 = 0; i4 < 3; i4++) {
            int j7 = x + seedRandomizer.nextInt(16);
            int k10 = seedRandomizer.nextInt(16);
            int i15 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(j7, k10, i15));
        }
        for (int j4 = 0; j4 < 4; j4++) {
            int k7 = x + seedRandomizer.nextInt(16);
            int l10 = seedRandomizer.nextInt(16) + seedRandomizer.nextInt(16);
            int j15 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(world, seedRandomizer, new BlockPos(k7, l10, j15));
        }
        for (int j3 = 0; j3 < 20; j3++) {
            int k6 = x + seedRandomizer.nextInt(16);
            int l9 = seedRandomizer.nextInt(64);
            int j14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(coolclk.skydimension.init.Blocks.SKY_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(k6, l9, j14));
        }
        d = 0.5D;

        NoiseGeneratorPerlin[] generatorCollection = new NoiseGeneratorPerlin[8];
        for (int j = 0; j < 8; j++) {
            generatorCollection[j] = new NoiseGeneratorPerlin(seedRandomizer, 0);
        }
        double k4_d2 = 0.0D;
        double k4_d3 = 1.0D;
        for (int i = 0; i < 8; i++) {
            k4_d2 += generatorCollection[i].getValue(((double) x * d) * k4_d3, ((double) z * d) * k4_d3) / k4_d3;
            k4_d3 /= 2D;
        }
        int k4 = (int) ((k4_d2 / 8D + seedRandomizer.nextDouble() * 4D + 4D) / 3D);

        int l7 = 0;
        if (seedRandomizer.nextInt(10) == 0) {
            l7++;
        }
        if (biome == Biomes.FOREST) {
            l7 += k4 + 5;
        }
        if (biome == Biomes.TAIGA) {
            l7 += k4 + 5;
        }
        if (biome == Biomes.DESERT) {
            l7 -= 20;
        }
        if (biome == Biomes.ICE_PLAINS) {
            l7 -= 20;
        }
        if (biome == Biomes.PLAINS) {
            l7 -= 20;
        }
        for (int i11 = 0; i11 < l7; i11++) {
            int k15 = x + seedRandomizer.nextInt(16) + 8;
            int j18 = z + seedRandomizer.nextInt(16) + 8;
            WorldGenerator worldgenerator = biome.getRandomTreeFeature(seedRandomizer);
            worldgenerator.generate(world, seedRandomizer, new BlockPos(k15, world.getHeight(k15, j18), j18));
        }
        for (int j11 = 0; j11 < 2; j11++) {
            int l15 = x + seedRandomizer.nextInt(16) + 8;
            int k18 = seedRandomizer.nextInt(128);
            int i21 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION)).generate(world, seedRandomizer, new BlockPos(l15, k18, i21));
        }

        if (seedRandomizer.nextInt(2) == 0) {
            int k11 = x + seedRandomizer.nextInt(16) + 8;
            int i16 = seedRandomizer.nextInt(128);
            int l18 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(world, seedRandomizer, new BlockPos(k11, i16, l18));
        }
        if (seedRandomizer.nextInt(4) == 0) {
            int l11 = x + seedRandomizer.nextInt(16) + 8;
            int j16 = seedRandomizer.nextInt(128);
            int i19 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.BROWN_MUSHROOM)).generate(world, seedRandomizer, new BlockPos(l11, j16, i19));
        }
        if (seedRandomizer.nextInt(8) == 0) {
            int i12 = x + seedRandomizer.nextInt(16) + 8;
            int k16 = seedRandomizer.nextInt(128);
            int j19 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.RED_MUSHROOM)).generate(world, seedRandomizer, new BlockPos(i12, k16, j19));
        }
        for (int j12 = 0; j12 < 10; j12++) {
            int l16 = x + seedRandomizer.nextInt(16) + 8;
            int k19 = seedRandomizer.nextInt(128);
            int j21 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenReed()).generate(world, seedRandomizer, new BlockPos(l16, k19, j21));
        }

        if (seedRandomizer.nextInt(32) == 0) {
            int k12 = x + seedRandomizer.nextInt(16) + 8;
            int i17 = seedRandomizer.nextInt(128);
            int l19 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(world, seedRandomizer, new BlockPos(k12, i17, l19));
        }
        int l12 = 0;
        if (biome == Biomes.DESERT) {
            l12 += 10;
        }
        for (int j17 = 0; j17 < l12; j17++) {
            int i20 = x + seedRandomizer.nextInt(16) + 8;
            int k21 = seedRandomizer.nextInt(128);
            int k22 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenCactus()).generate(world, seedRandomizer, new BlockPos(i20, k21, k22));
        }

        for (int k17 = 0; k17 < 50; k17++) {
            int j20 = x + seedRandomizer.nextInt(16) + 8;
            int l21 = seedRandomizer.nextInt(seedRandomizer.nextInt(120) + 8);
            int l22 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(world, seedRandomizer, new BlockPos(j20, l21, l22));
        }

        for (int l17 = 0; l17 < 20; l17++) {
            int k20 = x + seedRandomizer.nextInt(16) + 8;
            int i22 = seedRandomizer.nextInt(seedRandomizer.nextInt(seedRandomizer.nextInt(112) + 8) + 8);
            int i23 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(world, seedRandomizer, new BlockPos(k20, i22, i23));
        }

        for (int i18 = x + 8; i18 < x + 8 + 16; i18++) {
            for (int l20 = z + 8; l20 < z + 8 + 16; l20++) {
                int j22 = i18 - (x + 8);
                int j23 = l20 - (z + 8);
                int k23 = world.getTopSolidOrLiquidBlock(new BlockPos(i18, 0, l20)).getY();
                double d1 = world.getBiome(new BlockPos(j22, k23, j23)).getDefaultTemperature() - ((double) (k23 - 64) / 64D) * 0.29999999999999999D;
                BlockPos b1 = new BlockPos(i18, k23 - 1, l20);
                if (d1 < 0.5D && k23 > 0 && k23 < 128 && world.isAirBlock(new BlockPos(i18, k23, l20)) && world.getBlockState(b1).isSideSolid(world, b1, EnumFacing.UP) && world.getBlockState(new BlockPos(i18, k23 - 1, l20)).getMaterial() != Material.ICE) {
                    world.setBlockState(new BlockPos(i18, k23, l20), Blocks.SNOW.getDefaultState());
                }
            }
        }
        BlockSand.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z) {
        return false;
    }

    @Nonnull
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType enumCreatureType, @Nonnull BlockPos blockPos) {
        return Collections.singletonList(new Biome.SpawnListEntry(EntityChicken.class, 10, 4, 4));
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World world, @Nonnull String s, @Nonnull BlockPos blockPos, boolean b) {
        return null;
    }

    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(@Nonnull World world, @Nonnull String s, @Nonnull BlockPos blockPos) {
        return false;
    }

    private ChunkPrimer bytesToChunkPrimer(byte[] bytes) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 256; ++y) {
                    chunkPrimer.setBlockState(x, y, z, BlockSand.getBlockById(bytes[x << 11 | z << 7 | y]).getDefaultState());
                }
            }
        }
        return chunkPrimer;
    }

    private Chunk bytesToChunk(World worldIn, byte[] bytes, int chunkX, int chunkZ) {
        return new Chunk(worldIn, bytesToChunkPrimer(bytes), chunkX, chunkZ);
    }

    private final Random seedRandomizer;
    private final NoiseGeneratorOctaves field_28086_k;
    private final NoiseGeneratorOctaves field_28085_l;
    private final NoiseGeneratorOctaves field_28084_m;
    private final NoiseGeneratorOctaves field_28083_n;
    private final NoiseGeneratorOctaves field_28082_o;
    public final NoiseGeneratorOctaves field_28096_a;
    public final NoiseGeneratorOctaves field_28095_b;
    public final NoiseGeneratorOctaves field_28094_c;
    private final World world;
    private double[] field_28080_q;
    private double[] field_28079_r;
    private double[] field_28078_s;
    private double[] field_28077_t;
    private final MapGenBase caveGenerator;
    private Biome[] biomes;
    double[] field_28093_d;
    double[] field_28092_e;
    double[] field_28091_f;
    double[] field_28090_g;
    double[] field_28089_h;
}
