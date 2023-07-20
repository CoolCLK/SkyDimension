package coolclk.skydimension.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

import javax.annotation.Nullable;
import java.util.Random;

import static coolclk.skydimension.SkyDimension.LOGGER;

/*
在此文件中，可能与游戏源代码有差异，删除与优化了部分代码。
若你发现什么奇怪名称的变量，a1、d3、c114514之类的，不要去动它，那是生成地形特有的无意义变量名。

notch 特有的无意义代码（恼
 */

public class ChunkProviderSky implements IChunkProvider {
    public ChunkProviderSky(World world, long seed) {
        field_28079_r = new double[256];
        field_28078_s = new double[256];
        field_28077_t = new double[256];
        mapGeneratorBase = new MapGenCaves();
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
                    // 别用IDE推荐的优化方案，你要是真想优化，欢迎你优化。
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

    public void generateBiome(int x, int z, ChunkPrimer chunk, Biome biome) {
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

    @Nullable
    @Override
    public Chunk getLoadedChunk(int x, int z) {
        return provideChunk(x, z);
    }

    public Chunk provideChunk(int x, int z) {
        seedRandomizer.setSeed((long) x * 0x4f9939f508L + (long) z * 0x1ef1565bd5L);
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        Chunk chunk = new Chunk(world, chunkPrimer, x, z);
        Biome biome = world.getBiome(new BlockPos(x, 0, z));
        generateTerrain(x, z, chunkPrimer);
        generateBiome(x, z, chunkPrimer, biome);
        mapGeneratorBase.generate(world, x, z, chunkPrimer);
        chunk.generateSkylightMap();
        LOGGER.debug(chunk.getWorld().getProviderName());
        return chunk;
    }

    @Override
    public boolean tick() {
        return false;
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

    public String makeString() {
        return "RandomLevelSource";
    }

    @Override
    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }

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
}
