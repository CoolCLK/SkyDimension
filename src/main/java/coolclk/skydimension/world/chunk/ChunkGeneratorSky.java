package coolclk.skydimension.world.chunk;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static coolclk.skydimension.SkyDimension.LOGGER;

/*
在此文件中，可能与游戏源代码有差异，删除与优化了部分代码。
若你发现什么奇怪名称的变量，a1、d3、c114514之类的，不要去动它，那是生成地形特有的无意义变量名。

notch 特有的无意义代码（恼
 */

public class ChunkGeneratorSky implements IChunkGenerator {
    public final Random seedRandomizer;
    private final NoiseGeneratorOctaves noiseGeneratorOctaves_a;
    private final NoiseGeneratorOctaves noiseGeneratorOctaves_b;
    private final NoiseGeneratorOctaves noiseGeneratorOctaves_c;
    private final NoiseGeneratorOctaves noiseGeneratorOctaves_d;
    private final NoiseGeneratorOctaves noiseGeneratorOctaves_e;
    public NoiseGeneratorOctaves noiseGeneratorOctaves_f;
    public NoiseGeneratorOctaves noiseGeneratorOctaves_g;
    public NoiseGeneratorOctaves noiseGeneratorOctaves_h;
    private final World world;
    private double[] field_28080_q;
    private double[] field_28079_r;
    private double[] field_28078_s;
    private double[] field_28077_t;
    private final MapGenBase mapGeneratorBase;
    double[] field_28093_d;
    double[] field_28092_e;
    double[] field_28091_f;
    double[] field_28090_g;
    double[] field_28089_h;
    int[][] field_28088_i;

    public ChunkGeneratorSky(World world, long seed) {
        field_28079_r = new double[256];
        field_28078_s = new double[256];
        field_28077_t = new double[256];
        mapGeneratorBase = new MapGenBase();
        field_28088_i = new int[32][32];
        this.world = world;
        seedRandomizer = new Random(seed);
        noiseGeneratorOctaves_a = new NoiseGeneratorOctaves(seedRandomizer, 16);
        noiseGeneratorOctaves_b = new NoiseGeneratorOctaves(seedRandomizer, 16);
        noiseGeneratorOctaves_c = new NoiseGeneratorOctaves(seedRandomizer, 8);
        noiseGeneratorOctaves_d = new NoiseGeneratorOctaves(seedRandomizer, 4);
        noiseGeneratorOctaves_e = new NoiseGeneratorOctaves(seedRandomizer, 4);
        noiseGeneratorOctaves_f = new NoiseGeneratorOctaves(seedRandomizer, 10);
        noiseGeneratorOctaves_g = new NoiseGeneratorOctaves(seedRandomizer, 16);
        noiseGeneratorOctaves_h = new NoiseGeneratorOctaves(seedRandomizer, 8);
    }

    public void generateTerrain(int x, int z, ChunkPrimer chunk) {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        field_28080_q = generateRandomChunk(field_28080_q, x * byte0, z * byte0, k, byte1, l);
        for (int i1 = 0; i1 < byte0; i1++) {
            for (int j1 = 0; j1 < byte0; j1++) {
                for(int k1 = 0; k1 < 32; k1++) {
                    double d = 0.25D;
                    double d1 = field_28080_q[((i1) * l + (j1)) * byte1 + (k1)];
                    double d2 = field_28080_q[((i1) * l + (j1 + 1)) * byte1 + (k1)];
                    double d3 = field_28080_q[((i1 + 1) * l + (j1)) * byte1 + (k1)];
                    double d4 = field_28080_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1)];
                    double d5 = (field_28080_q[((i1) * l + (j1)) * byte1 + (k1 + 1)] - d1) * d;
                    double d6 = (field_28080_q[((i1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d2) * d;
                    double d7 = (field_28080_q[((i1 + 1) * l + (j1)) * byte1 + (k1 + 1)] - d3) * d;
                    double d8 = (field_28080_q[((i1 + 1) * l + (j1 + 1)) * byte1 + (k1 + 1)] - d4) * d;
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
                                Block l2 = Blocks.AIR;
                                if (d15 > 0.0D) {
                                    l2 = Blocks.STONE;
                                }
                                chunk.setBlockState(j2 % 8, j2 / 8, 8 - j2 % 8, l2.getDefaultState());
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

    public void generateBiomes(int x, int z, ChunkPrimer chunk, Biome biome) {
        double d = 0.03125D;
        field_28079_r = noiseGeneratorOctaves_d.generateNoiseOctaves(field_28079_r, x * 16, z * 16, 0, 16, 16, 1, d, d, 1.0D);
        field_28078_s = noiseGeneratorOctaves_d.generateNoiseOctaves(field_28078_s, x * 16, (int) 109.0134D, z * 16, 16, 1, 16, d, 1.0D, d);
        field_28077_t = noiseGeneratorOctaves_e.generateNoiseOctaves(field_28077_t, x * 16, z * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                int i1 = (int) (field_28077_t[k + l * 16] / 3D + 3D + seedRandomizer.nextDouble() * 0.25D);
                int j1 = -1;
                Block topBlock = biome.topBlock.getBlock();
                Block fillerBlock = biome.fillerBlock.getBlock();
                for (int k1 = 127; k1 >= 0; k1--) {
                    Block block = chunk.getBlockState(k, k1, l).getBlock();
                    if (block == Blocks.AIR) {
                        j1 = -1;
                        continue;
                    }
                    if (block != Blocks.STONE) {
                        continue;
                    }
                    if (j1 == -1) {
                        if (i1 <= 0) {
                            topBlock = Blocks.AIR;
                            fillerBlock = Blocks.STONE;
                        }
                        j1 = i1;
                        chunk.setBlockState(k, k1, l, topBlock.getDefaultState());
                        continue;
                    }
                    if (j1 <= 0) {
                        continue;
                    }
                    j1--;
                    chunk.setBlockState(k, k1, l, fillerBlock.getDefaultState());
                    if (j1 == 0 && fillerBlock == Blocks.SAND) {
                        j1 = seedRandomizer.nextInt(4);
                        fillerBlock = Blocks.SANDSTONE;
                    }
                }
            }
        }
    }

    private double[] generateRandomChunk(double[] ad, int i, int k, int l, int i1, int j1) {
        if (ad == null) {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        field_28090_g = noiseGeneratorOctaves_f.generateNoiseOctaves(field_28090_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_28089_h = noiseGeneratorOctaves_g.generateNoiseOctaves(field_28089_h, i, k, l, j1, 200D, 200D, 0.5D);
        d *= 2D;
        field_28093_d = noiseGeneratorOctaves_c.generateNoiseOctaves(field_28093_d, i, 0, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_28092_e = noiseGeneratorOctaves_a.generateNoiseOctaves(field_28092_e, i, 0, k, l, i1, j1, d, d1, d);
        field_28091_f = noiseGeneratorOctaves_b.generateNoiseOctaves(field_28091_f, i, 0, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        for (int j2 = 0; j2 < l; j2++) {
            for (int l2 = 0; l2 < j1; l2++) {
                for (int j3 = 0; j3 < i1; j3++) {
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
                    if(j3 > i1 - k3) {
                        double d13 = (float)(j3 - (i1 - k3)) / ((float)k3 - 1.0F);
                        d8 = d8 * (1.0D - d13) + -30D * d13;
                    }
                    k3 = 8;
                    if (j3 < k3) {
                        double d14 = (float)(k3 - j3) / ((float)k3 - 1.0F);
                        d8 = d8 * (1.0D - d14) + -30D * d14;
                    }
                    ad[k1] = d8;
                    k1++;
                }
            }
        }
        return ad;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        LOGGER.debug("Loading chunk: (x: " + x + ", z: " + z + ")");
        seedRandomizer.setSeed((long) x * 0x4f9939f508L + (long) z * 0x1ef1565bd5L);
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        Chunk chunk = new Chunk(world, chunkPrimer, x, z);
        Biome biome = world.getBiome(new BlockPos(x, 0, z));
        generateTerrain(x, z, chunkPrimer);
        generateBiomes(x, z, chunkPrimer, biome);
        mapGeneratorBase.generate(world, x, z, chunkPrimer);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        BlockSand.fallInstantly = true;
        int k = x * 16;
        int l = z * 16;
        Biome biome = world.getBiome(new BlockPos(k + 16, 0, l + 16));
        seedRandomizer.setSeed(world.getSeed());
        long l1 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        long l2 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        seedRandomizer.setSeed((long) x * l1 + (long) z * l2 ^ world.getSeed());
        double d;
        if (seedRandomizer.nextInt(4) == 0) {
            int i1 = k + seedRandomizer.nextInt(16) + 8;
            int l4 = seedRandomizer.nextInt(128);
            int i8 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(world, seedRandomizer, new BlockPos(i1, l4, i8));
        }
        if (seedRandomizer.nextInt(8) == 0) {
            int j1 = k + seedRandomizer.nextInt(16) + 8;
            int i5 = seedRandomizer.nextInt(seedRandomizer.nextInt(120) + 8);
            int j8 = l + seedRandomizer.nextInt(16) + 8;
            if (i5 < 64 || seedRandomizer.nextInt(10) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(world, seedRandomizer, new BlockPos(j1, i5, j8));
            }
        }
        for (int k1 = 0; k1 < 8; k1++) {
            int j5 = k + seedRandomizer.nextInt(16) + 8;
            int k8 = seedRandomizer.nextInt(128);
            int i13 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(world, seedRandomizer, new BlockPos(j5, k8, i13));
        }

        for (int i2 = 0; i2 < 10; i2++) {
            int k5 = k + seedRandomizer.nextInt(16);
            int l8 = seedRandomizer.nextInt(128);
            int j13 = l + seedRandomizer.nextInt(16);
            (new WorldGenClay(32)).generate(world, seedRandomizer, new BlockPos(k5, l8, j13));
        }
        for (int j2 = 0; j2 < 20; j2++) {
            int l5 = k + seedRandomizer.nextInt(16);
            int i9 = seedRandomizer.nextInt(128);
            int k13 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(l5, i9, k13));
        }
        for (int k2 = 0; k2 < 10; k2++) {
            int i6 = k + seedRandomizer.nextInt(16);
            int j9 = seedRandomizer.nextInt(128);
            int l13 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(i6, j9, l13));
        }

        for (int i3 = 0; i3 < 20; i3++) {
            int j6 = k + seedRandomizer.nextInt(16);
            int k9 = seedRandomizer.nextInt(128);
            int i14 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16)).generate(world, seedRandomizer, new BlockPos(j6, k9, i14));
        }
        for (int j3 = 0; j3 < 20; j3++) {
            int k6 = k + seedRandomizer.nextInt(16);
            int l9 = seedRandomizer.nextInt(64);
            int j14 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(k6, l9, j14));
        }
        for (int k3 = 0; k3 < 2; k3++) {
            int l6 = k + seedRandomizer.nextInt(16);
            int i10 = seedRandomizer.nextInt(32);
            int k14 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(l6, i10, k14));
        }
        for (int l3 = 0; l3 < 8; l3++) {
            int i7 = k + seedRandomizer.nextInt(16);
            int j10 = seedRandomizer.nextInt(16);
            int l14 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(i7, j10, l14));
        }
        for (int i4 = 0; i4 < 1; i4++) {
            int j7 = k + seedRandomizer.nextInt(16);
            int k10 = seedRandomizer.nextInt(16);
            int i15 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(j7, k10, i15));
        }
        for (int j4 = 0; j4 < 1; j4++) {
            int k7 = k + seedRandomizer.nextInt(16);
            int l10 = seedRandomizer.nextInt(16) + seedRandomizer.nextInt(16);
            int j15 = l + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(world, seedRandomizer, new BlockPos(k7, l10, j15));
        }
        d = 0.5D;

        NoiseGeneratorPerlin[] generatorCollection = new NoiseGeneratorPerlin[8];
        for (int j = 0; j < 8; j++) {
            generatorCollection[j] = new NoiseGeneratorPerlin(seedRandomizer, 0);
        }
        double k4_d2 = 0.0D;
        double k4_d3 = 1.0D;
        for (int i = 0; i < 8; i++) {
            k4_d2 += generatorCollection[i].getValue(((double) k * d) * k4_d3, ((double) l * d) * k4_d3) / k4_d3;
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
            int k15 = k + seedRandomizer.nextInt(16) + 8;
            int j18 = l + seedRandomizer.nextInt(16) + 8;
            WorldGenerator worldgenerator = biome.getRandomTreeFeature(seedRandomizer);
            worldgenerator.generate(world, seedRandomizer, new BlockPos(k15, world.getHeight(), j18));
        }
        for (int j11 = 0; j11 < 2; j11++) {
            int l15 = k + seedRandomizer.nextInt(16) + 8;
            int k18 = seedRandomizer.nextInt(128);
            int i21 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(world, seedRandomizer, new BlockPos(l15, k18, i21));
        }

        if (seedRandomizer.nextInt(2) == 0) {
            int k11 = k + seedRandomizer.nextInt(16) + 8;
            int i16 = seedRandomizer.nextInt(128);
            int l18 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(world, seedRandomizer, new BlockPos(k11, i16, l18));
        }
        if (seedRandomizer.nextInt(4) == 0) {
            int l11 = k + seedRandomizer.nextInt(16) + 8;
            int j16 = seedRandomizer.nextInt(128);
            int i19 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.BROWN_MUSHROOM)).generate(world, seedRandomizer, new BlockPos(l11, j16, i19));
        }
        if (seedRandomizer.nextInt(8) == 0) {
            int i12 = k + seedRandomizer.nextInt(16) + 8;
            int k16 = seedRandomizer.nextInt(128);
            int j19 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.RED_MUSHROOM)).generate(world, seedRandomizer, new BlockPos(i12, k16, j19));
        }
        for (int j12 = 0; j12 < 10; j12++) {
            int l16 = k + seedRandomizer.nextInt(16) + 8;
            int k19 = seedRandomizer.nextInt(128);
            int j21 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenReed()).generate(world, seedRandomizer, new BlockPos(l16, k19, j21));
        }

        if (seedRandomizer.nextInt(32) == 0) {
            int k12 = k + seedRandomizer.nextInt(16) + 8;
            int i17 = seedRandomizer.nextInt(128);
            int l19 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(world, seedRandomizer, new BlockPos(k12, i17, l19));
        }
        int l12 = 0;
        if (biome == Biomes.DESERT) {
            l12 += 10;
        }
        for (int j17 = 0; j17 < l12; j17++) {
            int i20 = k + seedRandomizer.nextInt(16) + 8;
            int k21 = seedRandomizer.nextInt(128);
            int k22 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenCactus()).generate(world, seedRandomizer, new BlockPos(i20, k21, k22));
        }

        for (int k17 = 0; k17 < 50; k17++) {
            int j20 = k + seedRandomizer.nextInt(16) + 8;
            int l21 = seedRandomizer.nextInt(seedRandomizer.nextInt(120) + 8);
            int l22 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(world, seedRandomizer, new BlockPos(j20, l21, l22));
        }

        for (int l17 = 0; l17 < 20; l17++) {
            int k20 = k + seedRandomizer.nextInt(16) + 8;
            int i22 = seedRandomizer.nextInt(seedRandomizer.nextInt(seedRandomizer.nextInt(112) + 8) + 8);
            int i23 = l + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(world, seedRandomizer, new BlockPos(k20, i22, i23));
        }

        for (int i18 = k + 8; i18 < k + 8 + 16; i18++) {
            for (int l20 = l + 8; l20 < l + 8 + 16; l20++) {
                int j22 = i18 - (k + 8);
                int j23 = l20 - (l + 8);
                int k23 = world.getTopSolidOrLiquidBlock(new BlockPos(i18, 0, l20)).getY();
                double d1 = world.getBiome(new BlockPos(j22, k23, j23)).getDefaultTemperature() - ((double) (k23 - 64) / 64D) * 0.29999999999999999D;
                if (d1 < 0.5D && k23 > 0 && k23 < 128 && world.isAirBlock(new BlockPos(i18, k23, l20)) && world.getBlockState(new BlockPos(i18, k23 - 1, l20)).isTopSolid() && world.getBlockState(new BlockPos(i18, k23 - 1, l20)).getMaterial() != Material.ICE) {
                    world.setBlockState(new BlockPos(i18, k23, l20), Blocks.SNOW.getDefaultState());
                }
            }

        }
        BlockSand.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
        Biome Biome = world.getBiome(blockPos);
        return Biome == null ? null : Biome.getSpawnableList(enumCreatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World world, String s, BlockPos blockPos, boolean b) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World world, String s, BlockPos blockPos) {
        return false;
    }
}
