package coolclk.skydimension.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.src.NoiseGeneratorOctaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class ChunkProviderSky implements IChunkProvider {
    public ChunkProviderSky(World world, long l) {
        field_28079_r = new double[256];
        field_28078_s = new double[256];
        field_28077_t = new double[256];
        field_28076_u = new MapGenCaves();
        field_28088_i = new int[32][32];
        field_28081_p = world;
        field_28087_j = new Random(l);
        field_28086_k = new NoiseGeneratorOctaves(field_28087_j, 16);
        field_28085_l = new NoiseGeneratorOctaves(field_28087_j, 16);
        field_28084_m = new NoiseGeneratorOctaves(field_28087_j, 8);
        field_28083_n = new NoiseGeneratorOctaves(field_28087_j, 4);
        field_28082_o = new NoiseGeneratorOctaves(field_28087_j, 4);
        field_28096_a = new NoiseGeneratorOctaves(field_28087_j, 10);
        field_28095_b = new NoiseGeneratorOctaves(field_28087_j, 16);
        field_28094_c = new NoiseGeneratorOctaves(field_28087_j, 8);
    }

    public void func_28071_a(int i, int j, ChunkPrimer primer) {
        byte byte0 = 2;
        int k = byte0 + 1;
        byte byte1 = 33;
        int l = byte0 + 1;
        field_28080_q = updateChunks(field_28080_q, i * byte0, 0, j * byte0, k, byte1, l);
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
                                primer.setBlockState(j2 % 8, j2 / 8, 8 - j2 % 8, l2.getDefaultState());
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

    public void func_28072_a(int i, int j, ChunkPrimer primer, Biome biome) {
        double d = 0.03125D;
        field_28079_r = field_28083_n.generateNoiseOctaves(field_28079_r, i * 16, j * 16, 0, 16, 16, 1, d, d, 1.0D);
        field_28078_s = field_28083_n.generateNoiseOctaves(field_28078_s, i * 16, (int) 109.0134D, j * 16, 16, 1, 16, d, 1.0D, d);
        field_28077_t = field_28082_o.generateNoiseOctaves(field_28077_t, i * 16, j * 16, 0, 16, 16, 1, d * 2D, d * 2D, d * 2D);
        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                int i1 = (int) (field_28077_t[k + l * 16] / 3D + 3D + field_28087_j.nextDouble() * 0.25D);
                int j1 = -1;
                Block topBlock = biome.topBlock.getBlock();
                Block fillerBlock = biome.fillerBlock.getBlock();
                for(int k1 = 127; k1 >= 0; k1--) {
                    Block block = primer.getBlockState(k, k1, l).getBlock();
                    if (block == Blocks.AIR) {
                        j1 = -1;
                        continue;
                    }
                    if (block != Blocks.STONE) {
                        continue;
                    }
                    if (j1 == -1) {
                        if(i1 <= 0) {
                            topBlock = Blocks.AIR;
                            fillerBlock = Blocks.STONE;
                        }
                        j1 = i1;
                        if (k1 >= 0) {
                            block = topBlock;
                        } else {
                            block = fillerBlock;
                        }
                        continue;
                    }
                    if (j1 <= 0) {
                        continue;
                    }
                    j1--;
                    block = fillerBlock;
                    if(j1 == 0 && fillerBlock == Blocks.SAND) {
                        j1 = field_28087_j.nextInt(4);
                        fillerBlock = Blocks.SANDSTONE;
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        return provideChunk(x, z);
    }

    public Chunk provideChunk(int i, int j) {
        field_28087_j.setSeed((long) i * 0x4f9939f508L + (long) j * 0x1ef1565bd5L);
        ChunkPrimer primers = new ChunkPrimer();
        Chunk chunk = new Chunk(field_28081_p, primers, i, j);
        field_28075_v = field_28081_p.getBiome(new BlockPos(i, 0, j));
        func_28071_a(i, j, primers);
        func_28072_a(i, j, primers, field_28075_v);
        field_28076_u.generate(field_28081_p, i, j, primers);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public boolean tick() {
        return false;
    }

    private double[] updateChunks(double ad[], int i, int j, int k, int l, int i1, int j1) {
        if(ad == null)
        {
            ad = new double[l * i1 * j1];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        field_28090_g = field_28096_a.func_4109_a(field_28090_g, i, k, l, j1, 1.121D, 1.121D, 0.5D);
        field_28089_h = field_28095_b.func_4109_a(field_28089_h, i, k, l, j1, 200D, 200D, 0.5D);
        d *= 2D;
        field_28093_d = field_28084_m.generateNoiseOctaves(field_28093_d, i, j, k, l, i1, j1, d / 80D, d1 / 160D, d / 80D);
        field_28092_e = field_28086_k.generateNoiseOctaves(field_28092_e, i, j, k, l, i1, j1, d, d1, d);
        field_28091_f = field_28085_l.generateNoiseOctaves(field_28091_f, i, j, k, l, i1, j1, d, d1, d);
        int k1 = 0;
        int l1 = 0;
        int i2 = 16 / l;
        for(int j2 = 0; j2 < l; j2++)
        {
            int k2 = j2 * i2 + i2 / 2;
            for(int l2 = 0; l2 < j1; l2++)
            {
                int i3 = l2 * i2 + i2 / 2;
                double d2 = field_28081_p.getBiome(new BlockPos(l2, i2, i2 / 2)).getDefaultTemperature();
                double d3 = field_28081_p.getBiome(new BlockPos(k2 * 16, d2, i3)).isHighHumidity() ? 1 : 0;
                double d4 = 1 - d3;
                d4 *= d4;
                d4 *= d4;
                d4 = 1 - d4;
                double d5 = (field_28090_g[l1] + 256D) / 512D;
                d5 *= d4;
                if (d5 > 1) {
                    d5 = 1;
                }
                double d6 = field_28089_h[l1] / 8000D;
                if(d6 < 0)
                {
                    d6 = -d6 * 0.3D;
                }
                d6 = d6 * 3D - 2D;
                if(d6 > 1.0D) {
                    d6 = 1.0D;
                }
                d6 /= 8D;
                d6 = 0.0D;
                if(d5 < 0.0D) {
                    d5 = 0.0D;
                }
                d5 += 0.5D;
                d6 = (d6 * (double)i1) / 16D;
                l1++;
                double d7 = (double)i1 / 2D;
                for (int j3 = 0; j3 < i1; j3++) {
                    double d8 = 0.0D;
                    double d9 = (((double)j3 - d7) * 8D) / d5;
                    if (d9 < 0.0D) {
                        d9 *= -1D;
                    }
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

    public boolean chunkExists(int i, int j) {
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        BlockSand.fallInstantly = true;
        int k = i * 16;
        int l = j * 16;
        Biome biome = field_28081_p.getBiome(new BlockPos(k + 16, 0, l + 16));
        field_28087_j.setSeed(field_28081_p.getSeed());
        long l1 = (field_28087_j.nextLong() / 2L) * 2L + 1L;
        long l2 = (field_28087_j.nextLong() / 2L) * 2L + 1L;
        field_28087_j.setSeed((long)i * l1 + (long)j * l2 ^ field_28081_p.getSeed());
        double d = 0.25D;
        if (field_28087_j.nextInt(4) == 0) {
            int i1 = k + field_28087_j.nextInt(16) + 8;
            int l4 = field_28087_j.nextInt(128);
            int i8 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(field_28081_p, field_28087_j, new BlockPos(i1, l4, i8));
        }
        if (field_28087_j.nextInt(8) == 0) {
            int j1 = k + field_28087_j.nextInt(16) + 8;
            int i5 = field_28087_j.nextInt(field_28087_j.nextInt(120) + 8);
            int j8 = l + field_28087_j.nextInt(16) + 8;
            if (i5 < 64 || field_28087_j.nextInt(10) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(field_28081_p, field_28087_j, new BlockPos(j1, i5, j8));
            }
        }
        for (int k1 = 0; k1 < 8; k1++) {
            int j5 = k + field_28087_j.nextInt(16) + 8;
            int k8 = field_28087_j.nextInt(128);
            int i13 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(field_28081_p, field_28087_j, new BlockPos(j5, k8, i13));
        }

        for (int i2 = 0; i2 < 10; i2++) {
            int k5 = k + field_28087_j.nextInt(16);
            int l8 = field_28087_j.nextInt(128);
            int j13 = l + field_28087_j.nextInt(16);
            (new WorldGenClay(32)).generate(field_28081_p, field_28087_j, new BlockPos(k5, l8, j13));
        }

        for (int j2 = 0; j2 < 20; j2++) {
            int l5 = k + field_28087_j.nextInt(16);
            int i9 = field_28087_j.nextInt(128);
            int k13 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(field_28081_p, field_28087_j, new BlockPos(l5, i9, k13));
        }

        for (int k2 = 0; k2 < 10; k2++) {
            int i6 = k + field_28087_j.nextInt(16);
            int j9 = field_28087_j.nextInt(128);
            int l13 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(field_28081_p, field_28087_j, new BlockPos(i6, j9, l13));
        }

        for (int i3 = 0; i3 < 20; i3++) {
            int j6 = k + field_28087_j.nextInt(16);
            int k9 = field_28087_j.nextInt(128);
            int i14 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16)).generate(field_28081_p, field_28087_j, new BlockPos(j6, k9, i14));
        }

        for (int j3 = 0; j3 < 20; j3++) {
            int k6 = k + field_28087_j.nextInt(16);
            int l9 = field_28087_j.nextInt(64);
            int j14 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(field_28081_p, field_28087_j, new BlockPos(k6, l9, j14));
        }

        for (int k3 = 0; k3 < 2; k3++) {
            int l6 = k + field_28087_j.nextInt(16);
            int i10 = field_28087_j.nextInt(32);
            int k14 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(field_28081_p, field_28087_j, new BlockPos(l6, i10, k14));
        }

        for (int l3 = 0; l3 < 8; l3++) {
            int i7 = k + field_28087_j.nextInt(16);
            int j10 = field_28087_j.nextInt(16);
            int l14 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(field_28081_p, field_28087_j, new BlockPos(i7, j10, l14));
        }

        for (int i4 = 0; i4 < 1; i4++) {
            int j7 = k + field_28087_j.nextInt(16);
            int k10 = field_28087_j.nextInt(16);
            int i15 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(field_28081_p, field_28087_j, new BlockPos(j7, k10, i15));
        }

        for (int j4 = 0; j4 < 1; j4++) {
            int k7 = k + field_28087_j.nextInt(16);
            int l10 = field_28087_j.nextInt(16) + field_28087_j.nextInt(16);
            int j15 = l + field_28087_j.nextInt(16);
            (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(field_28081_p, field_28087_j, new BlockPos(k7, l10, j15));
        }

        d = 0.5D;
        int k4 = (int) ((field_28094_c.func_806_a((double) k * d, (double) l * d) / 8D + field_28087_j.nextDouble() * 4D + 4D) / 3D);
        int l7 = 0;
        if (field_28087_j.nextInt(10) == 0) {
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
            int k15 = k + field_28087_j.nextInt(16) + 8;
            int j18 = l + field_28087_j.nextInt(16) + 8;
            WorldGenerator worldgenerator = biome.getRandomTreeFeature(field_28087_j);
            worldgenerator.generate(field_28081_p, field_28087_j, new BlockPos(k15, field_28081_p.getHeight(), j18));
        }
        for (int j11 = 0; j11 < 2; j11++) {
            int l15 = k + field_28087_j.nextInt(16) + 8;
            int k18 = field_28087_j.nextInt(128);
            int i21 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(field_28081_p, field_28087_j, new BlockPos(l15, k18, i21));
        }

        if (field_28087_j.nextInt(2) == 0) {
            int k11 = k + field_28087_j.nextInt(16) + 8;
            int i16 = field_28087_j.nextInt(128);
            int l18 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(field_28081_p, field_28087_j, new BlockPos(k11, i16, l18));
        }
        if (field_28087_j.nextInt(4) == 0) {
            int l11 = k + field_28087_j.nextInt(16) + 8;
            int j16 = field_28087_j.nextInt(128);
            int i19 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenBush(Blocks.BROWN_MUSHROOM)).generate(field_28081_p, field_28087_j, new BlockPos(l11, j16, i19));
        }
        if (field_28087_j.nextInt(8) == 0) {
            int i12 = k + field_28087_j.nextInt(16) + 8;
            int k16 = field_28087_j.nextInt(128);
            int j19 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenBush(Blocks.RED_MUSHROOM)).generate(field_28081_p, field_28087_j, new BlockPos(i12, k16, j19));
        }
        for (int j12 = 0; j12 < 10; j12++) {
            int l16 = k + field_28087_j.nextInt(16) + 8;
            int k19 = field_28087_j.nextInt(128);
            int j21 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenReed()).generate(field_28081_p, field_28087_j, new BlockPos(l16, k19, j21));
        }

        if(field_28087_j.nextInt(32) == 0)
        {
            int k12 = k + field_28087_j.nextInt(16) + 8;
            int i17 = field_28087_j.nextInt(128);
            int l19 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(field_28081_p, field_28087_j, new BlockPos(k12, i17, l19));
        }
        int l12 = 0;
        if (biome == Biomes.DESERT) {
            l12 += 10;
        }
        for (int j17 = 0; j17 < l12; j17++) {
            int i20 = k + field_28087_j.nextInt(16) + 8;
            int k21 = field_28087_j.nextInt(128);
            int k22 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenCactus()).generate(field_28081_p, field_28087_j, new BlockPos(i20, k21, k22));
        }

        for (int k17 = 0; k17 < 50; k17++) {
            int j20 = k + field_28087_j.nextInt(16) + 8;
            int l21 = field_28087_j.nextInt(field_28087_j.nextInt(120) + 8);
            int l22 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(field_28081_p, field_28087_j, new BlockPos(j20, l21, l22));
        }

        for (int l17 = 0; l17 < 20; l17++) {
            int k20 = k + field_28087_j.nextInt(16) + 8;
            int i22 = field_28087_j.nextInt(field_28087_j.nextInt(field_28087_j.nextInt(112) + 8) + 8);
            int i23 = l + field_28087_j.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(field_28081_p, field_28087_j, new BlockPos(k20, i22, i23));
        }

        for (int i18 = k + 8; i18 < k + 8 + 16; i18++) {
            for (int l20 = l + 8; l20 < l + 8 + 16; l20++) {
                int j22 = i18 - (k + 8);
                int j23 = l20 - (l + 8);
                int k23 = field_28081_p.getTopSolidOrLiquidBlock(new BlockPos(i18, 0, l20)).getY();
                double d1 = field_28081_p.getBiome(new BlockPos(j22, k23, j23)).getDefaultTemperature() - ((double)(k23 - 64) / 64D) * 0.29999999999999999D;
                if (d1 < 0.5D && k23 > 0 && k23 < 128 && field_28081_p.isAirBlock(new BlockPos(i18, k23, l20)) && field_28081_p.getBlockState(new BlockPos(i18, k23 - 1, l20)).isTopSolid() && field_28081_p.getBlockState(new BlockPos(i18, k23 - 1, l20)).getMaterial() != Material.ICE) {
                    field_28081_p.setBlockState(new BlockPos(i18, k23, l20), Blocks.SNOW.getDefaultState());
                }
            }

        }
        BlockSand.fallInstantly = false;
    }

    public String makeString() {
        return "RandomLevelSource";
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }

    private Random field_28087_j;
    private NoiseGeneratorOctaves field_28086_k;
    private NoiseGeneratorOctaves field_28085_l;
    private NoiseGeneratorOctaves field_28084_m;
    private NoiseGeneratorOctaves field_28083_n;
    private NoiseGeneratorOctaves field_28082_o;
    public NoiseGeneratorOctaves field_28096_a;
    public NoiseGeneratorOctaves field_28095_b;
    public NoiseGeneratorOctaves field_28094_c;
    private World field_28081_p;
    private double field_28080_q[];
    private double field_28079_r[];
    private double field_28078_s[];
    private double field_28077_t[];
    private MapGenBase field_28076_u;
    private Biome field_28075_v;
    double field_28093_d[];
    double field_28092_e[];
    double field_28091_f[];
    double field_28090_g[];
    double field_28089_h[];
    int field_28088_i[][];
    private double field_28074_w[];
}
