package coolclk.skydimension.forge.world.gen;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.MapGenVillage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * The chunk generator for sky dimension.<br>
 * <br>
 * <i>I have been re-write the code without any anti-obfuscation. What's a shit time.</i><br>
 * <i><strong>Note: </strong>original author is notch.</i>
 * @author CoolCLK
 */
public class ChunkGeneratorSky implements IChunkGenerator {
    private final Random seedRandomizer;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private ChunkGeneratorSettings settings;
    private double[] undergroundTemplates;
    private double[] biomeTemplates;
    private final MapGenCaves caveGenerator;
    private final MapGenVillage villageGenerator;

    /**
     * THe function will be involved when sky dimension started to generate.<br>
     * @author CoolCLK
     */
    public ChunkGeneratorSky(World world) {
        this.biomeTemplates = new double[256];
        this.world = world;
        this.mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
        this.seedRandomizer = new Random(world.getSeed());
        this.caveGenerator = new MapGenCaves();
        this.villageGenerator = new MapGenVillage();

        this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
        this.world.setSeaLevel(this.settings.seaLevel);

        field_28093_d_g = new NoiseGeneratorOctaves(seedRandomizer, 8);
        field_28093_d_f = new NoiseGeneratorOctaves(seedRandomizer, 16);
        field_28093_d_e = new NoiseGeneratorOctaves(seedRandomizer, 16);
    }

    /**
     * Generate underground part of dimension.<br>
     * @author CoolCLK
     */
    public void generateUnderground(int xOffset, int zOffset, ChunkPrimer chunk) {
        byte scale = 2;
        int xSize = scale + 1;
        byte ySize = 33;
        int zSize = scale + 1;
        undergroundTemplates = generateANoiseOctave(undergroundTemplates, xOffset * scale, zOffset * scale, xSize, ySize, zSize);
        for (int partX = 0; partX < scale; partX++) {
            for (int partZ = 0; partZ < scale; partZ++) {
                for (int partY = 0; partY < 32; partY++) {
                    double d = 0.25D;
                    double d1 = undergroundTemplates[((partX) * zSize + (partZ)) * ySize + (partY)];
                    double d2 = undergroundTemplates[((partX) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d3 = undergroundTemplates[((partX + 1) * zSize + (partZ)) * ySize + (partY)];
                    double d4 = undergroundTemplates[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d5 = (undergroundTemplates[((partX) * zSize + (partZ)) * ySize + (partY + 1)] - d1) * d;
                    double d6 = (undergroundTemplates[((partX) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d2) * d;
                    double d7 = (undergroundTemplates[((partX + 1) * zSize + (partZ)) * ySize + (partY + 1)] - d3) * d;
                    double d8 = (undergroundTemplates[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d4) * d;
                    for (int blockY = 0; blockY < 4; blockY++) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int blockX = 0; blockX < 8; blockX++) {
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int blockZ = 0; blockZ < 8; blockZ++) {
                                IBlockState blockState = Blocks.AIR.getDefaultState();
                                if (d15 > 0.0D) {
                                    blockState = Blocks.STONE.getDefaultState();
                                }

                                List<BlockPos> positions = new ArrayList<>();
                                positions.add(new BlockPos(
                                        (blockX + partX * 8) /* part x */,
                                        (partY * 4 + blockY) /* part y */ + ((blockZ % 2) /* each 256 height part */ * '\200' /* 128 height part */),
                                        (partZ * 8) /* part z */ + (blockZ /* each z */)
                                ));
                                if (blockZ % 2 == 1) {
                                    positions.add(new BlockPos(
                                            (blockX + partX * 8) /* part x */,
                                            (partY * 4 + blockY) /* part y */ + '\200',
                                            (partZ * 8) - 1 /* part z */ + (blockZ /* each z */)
                                    ));
                                    positions.add(new BlockPos(
                                            (blockX + partX * 8) /* part x */,
                                            (partY * 4 + blockY) /* part y */,
                                            (partZ * 8) /* part z */ + (blockZ /* each z */)
                                    ));
                                }
                                for (BlockPos positon : positions) {
                                    chunk.setBlockState(positon.getX(), positon.getY(), positon.getZ(), blockState);
                                }
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

    /**
     * Generate biomes part of dimension.<br>
     * @author CoolCLK
     */
    public void generateBiomes(int x, int z, ChunkPrimer chunkPrimer, Biome[] abiome) {
        double offset = 0.03125D;
        NoiseGeneratorOctaves noiseGenerator = new NoiseGeneratorOctaves(seedRandomizer, 4);
        biomeTemplates = noiseGenerator.generateNoiseOctaves(biomeTemplates, x * 16, z * 16, 0, 16, 16, 1, offset * 2D, offset * 2D, offset * 2D);
        for (int gx = 0; gx < 16; gx++) {
            for (int gz = 0; gz < 16; gz++) {
                Biome biome = abiome[gx + gz * 16];
                int i1 = (int) (biomeTemplates[gx + gz * 16] / 3D + 3D + seedRandomizer.nextDouble() * 0.25D);
                int j1 = -1;
                IBlockState top = biome.topBlock;
                IBlockState filler = biome.fillerBlock;
                for (int gy = 127; gy >= 0; gy--) {
                    IBlockState state = chunkPrimer.getBlockState(gx, gy, gz);
                    if (state.getBlock() == Blocks.AIR) {
                        j1 = -1;
                        continue;
                    }
                    if (state.getBlock() != Blocks.STONE) {
                        continue;
                    }
                    if (j1 == -1) {
                        if (i1 <= 0) {
                            top = Blocks.AIR.getDefaultState();
                            filler = Blocks.STONE.getDefaultState();
                        }
                        j1 = i1;
                        chunkPrimer.setBlockState(gx, gy, gz, top);
                        continue;
                    }
                    if (j1 <= 0) {
                        continue;
                    }
                    j1--;
                    chunkPrimer.setBlockState(gx, gy, gz, filler);
                    if (j1 == 0 && filler.getBlock() == Blocks.SAND) {
                        j1 = seedRandomizer.nextInt(4);
                        filler = Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }
        }
    }

    NoiseGeneratorOctaves field_28093_d_g;
    NoiseGeneratorOctaves field_28093_d_f;
    NoiseGeneratorOctaves field_28093_d_e;
    /**
     * Generate a noise octave.<br>
     * <i>I gave up to re-write it.</i>
     * @author notch
     */
    private double[] generateANoiseOctave(double[] ad, int xOffset, int zOffset, int xSize, int ySize, int zSize) {
        if (ad == null) {
            ad = new double[xSize * ySize * zSize];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        d *= 2D;
        double[] field_28093_d = null;
        double[] field_28091_f = null;
        double[] field_28092_e = null;
        field_28093_d = field_28093_d_g.generateNoiseOctaves(field_28093_d, xOffset, 0, zOffset, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
        field_28092_e = field_28093_d_f.generateNoiseOctaves(field_28092_e, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
        field_28091_f = field_28093_d_e.generateNoiseOctaves(field_28091_f, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
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

    /**
     * Generate chunk of dimension.<br>
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        this.seedRandomizer.setSeed((long) chunkX * 0x4f9939f508L + (long) chunkX * 0x1ef1565bd5L);
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        this.generateUnderground(chunkX, chunkZ, chunkPrimer);
        this.generateBiomes(chunkX, chunkZ, chunkPrimer, world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16));
        if (this.settings.useCaves) {
            this.caveGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
        }

        if (this.mapFeaturesEnabled) {
            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
            }
        }

        Chunk chunk = new Chunk(world, chunkPrimer, chunkX, chunkZ);

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * Populate chunk of dimension.<br>
     * @author CoolCLK
     */
    @Override
    public void populate(int chunkX, int chunkZ) {
        BlockSand.fallInstantly = true;

        int x = chunkX * 16;
        int z = chunkZ * 16;
        Biome biome = world.getBiome(new BlockPos(x + 16, 0, z + 16));

        this.villageGenerator.generateStructure(this.world, this.seedRandomizer, new ChunkPos(chunkX, chunkZ));

        seedRandomizer.setSeed(world.getSeed());
        long l1 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        long l2 = (seedRandomizer.nextLong() / 2L) * 2L + 1L;
        seedRandomizer.setSeed((long) chunkX * l1 + (long) chunkZ * l2 ^ world.getSeed());
        if (seedRandomizer.nextInt(4) == 0) {
            int i1 = x + seedRandomizer.nextInt(16) + 8;
            int l4 = seedRandomizer.nextInt(256);
            int i8 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(world, seedRandomizer, new BlockPos(i1, l4, i8));
        }
        if (seedRandomizer.nextInt(8) == 0) {
            int j1 = x + seedRandomizer.nextInt(16) + 8;
            int i5 = seedRandomizer.nextInt(seedRandomizer.nextInt(240) + 8);
            int j8 = z + seedRandomizer.nextInt(16) + 8;
            if (i5 < 64 || seedRandomizer.nextInt(10) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(world, seedRandomizer, new BlockPos(j1, i5, j8));
            }
        }
        for (int k1 = 0; k1 < 8; k1++) {
            int j5 = x + seedRandomizer.nextInt(16) + 8;
            int k8 = seedRandomizer.nextInt(256);
            int i13 = z + seedRandomizer.nextInt(16) + 8;
            (new WorldGenDungeons()).generate(world, seedRandomizer, new BlockPos(j5, k8, i13));
        }
        for (int i2 = 0; i2 < 10; i2++) {
            int k5 = x + seedRandomizer.nextInt(16);
            int l8 = seedRandomizer.nextInt(256);
            int j13 = z + seedRandomizer.nextInt(16);
            (new WorldGenClay(32)).generate(world, seedRandomizer, new BlockPos(k5, l8, j13));
        }
        for (int j2 = 0; j2 < 20; j2++) {
            int l5 = x + seedRandomizer.nextInt(16);
            int i9 = seedRandomizer.nextInt(256);
            int k13 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(l5, i9, k13));
        }
        for (int k2 = 0; k2 < 10; k2++) {
            int i6 = x + seedRandomizer.nextInt(16);
            int j9 = seedRandomizer.nextInt(256);
            int l13 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(world, seedRandomizer, new BlockPos(i6, j9, l13));
        }
        for (int i3 = 0; i3 < 30; i3++) {
            int j6 = x + seedRandomizer.nextInt(16);
            int k9 = seedRandomizer.nextInt(256);
            int i14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16)).generate(world, seedRandomizer, new BlockPos(j6, k9, i14));
        }
        for (int j3 = 0; j3 < 25; j3++) {
            int k6 = x + seedRandomizer.nextInt(16);
            int l9 = seedRandomizer.nextInt(128);
            int j14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(k6, l9, j14));
        }
        for (int k3 = 0; k3 < 3; k3++) {
            int l6 = x + seedRandomizer.nextInt(16);
            int i10 = seedRandomizer.nextInt(128);
            int k14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(world, seedRandomizer, new BlockPos(l6, i10, k14));
        }
        for (int l3 = 0; l3 < 12; l3++) {
            int i7 = x + seedRandomizer.nextInt(16);
            int j10 = seedRandomizer.nextInt(128);
            int l14 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(i7, j10, l14));
        }
        for (int i4 = 0; i4 < 2; i4++) {
            int j7 = x + seedRandomizer.nextInt(16);
            int k10 = seedRandomizer.nextInt(64);
            int i15 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(j7, k10, i15));
        }
        for (int i4 = 0; i4 < 1; i4++) {
            int j7 = x + seedRandomizer.nextInt(16);
            int k10 = seedRandomizer.nextInt(64);
            int i15 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), 7)).generate(world, seedRandomizer, new BlockPos(j7, k10, i15));
        }
        for (int j4 = 0; j4 < 2; j4++) {
            int k7 = x + seedRandomizer.nextInt(16);
            int l10 = seedRandomizer.nextInt(64) + seedRandomizer.nextInt(16);
            int j15 = z + seedRandomizer.nextInt(16);
            (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(world, seedRandomizer, new BlockPos(k7, l10, j15));
        }

        double d = 0.5D;
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

        if (biome == Biomes.DESERT) {
            for (int j17 = 0; j17 < 10; j17++) {
                int i20 = x + seedRandomizer.nextInt(16) + 8;
                int k21 = seedRandomizer.nextInt(128);
                int k22 = z + seedRandomizer.nextInt(16) + 8;
                (new WorldGenCactus()).generate(world, seedRandomizer, new BlockPos(i20, k21, k22));
            }
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

        BlockSand.fallInstantly = false;
    }

    /**
     * Generate structures of dimension.<br>
     * @return Is a successful generate?
     * @author CoolCLK
     */
    @Override
    public boolean generateStructures(@Nonnull Chunk chunk, int chunkX, int chunkZ) {
        return false;
    }

    /**
     * Get spawn entities of dimension.<br>
     * @return The possible spawns of the biome
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos blockPos) {
        return creatureType == EnumCreatureType.CREATURE ? Collections.singletonList(new Biome.SpawnListEntry(EntityChicken.class, 10, 1, 4)) : Collections.emptyList();
    }

    /**
     * Get the nearest structure of dimension.<br>
     * @return The position of the nearest structure, or return null
     * @author CoolCLK
     */
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World world, @Nonnull String structureName, @Nonnull BlockPos blockPos, boolean findUnexplored) {
        if (!this.mapFeaturesEnabled) {
            return null;
        } else if ("Village".equals(structureName) && this.villageGenerator != null) {
            return this.villageGenerator.getNearestStructurePos(world, blockPos, findUnexplored);
        } else {
            return null;
        }
    }

    /**
     * Recreate structures of dimension.<br>
     * @author CoolCLK
     */
    @Override
    public void recreateStructures(@Nonnull Chunk chunk, int x, int z) {
        if (this.mapFeaturesEnabled) {
            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, x, z, null);
            }
        }
    }

    /**
     * Check if in the structure of dimension.<br>
     * @author CoolCLK
     */
    @Override
    public boolean isInsideStructure(@Nonnull World world, @Nonnull String structureName, @Nonnull BlockPos blockPos) {
        if (!this.mapFeaturesEnabled) {
            return false;
        } else if ("Village".equals(structureName) && this.villageGenerator != null) {
            return this.villageGenerator.isInsideStructure(blockPos);
        } else {
            return false;
        }
    }
}
