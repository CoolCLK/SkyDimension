package coolclk.skydimension.forge.world.gen;

import coolclk.skydimension.forge.world.WorldProviderSky;
import coolclk.skydimension.forge.world.gen.structure.MapGenFloatingBoat;
import coolclk.skydimension.forge.world.gen.structure.MapGenStrongholdPortalRoom;
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
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * The chunk generator for sky dimension.<br>
 * <br>
 * <i>I have been re-write the code without any deobfuscation. Don't think the code will bring you a good read.</i><br>
 * <i><strong>Note: </strong>original author is notch.</i>
 * @author CoolCLK
 */
public class ChunkGeneratorSky implements IChunkGenerator {
    private final NoiseGeneratorOctaves xNoiseGenerator;
    private final NoiseGeneratorOctaves yNoiseGenerator;
    private final NoiseGeneratorOctaves zNoiseGenerator;
    private final Random randomizer;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final ChunkGeneratorSettings settings;
    private double[] undergroundBuffer;
    private double[] biomeBuffer;
    private final MapGenCaves caveGenerator;
    private final MapGenVillage villageGenerator;
    private final MapGenStrongholdPortalRoom strongholdGenerator;
    private final MapGenFloatingBoat floatingBoatGenerator;
    private final List<? extends MapGenStructure> allowedStructureGenerators;

    /**
     * THe function will be involved when sky dimension started to generate.<br>
     * @author CoolCLK
     */
    public ChunkGeneratorSky(World world) {
        this.biomeBuffer = new double[256];
        this.world = world;
        this.mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
        this.randomizer = new Random(world.getSeed());
        this.caveGenerator = new MapGenCaves();
        this.villageGenerator = new MapGenVillage();
        this.strongholdGenerator = new MapGenStrongholdPortalRoom();
        this.floatingBoatGenerator = new MapGenFloatingBoat();
        this.allowedStructureGenerators = Arrays.asList(this.villageGenerator, this.strongholdGenerator, this.floatingBoatGenerator);

        this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(world.getWorldInfo().getGeneratorOptions()).build();
        this.world.setSeaLevel(this.settings.seaLevel);

        xNoiseGenerator = new NoiseGeneratorOctaves(randomizer, 8);
        yNoiseGenerator = new NoiseGeneratorOctaves(randomizer, 16);
        zNoiseGenerator = new NoiseGeneratorOctaves(randomizer, 16);
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
        undergroundBuffer = generateANoiseOctave(undergroundBuffer, xOffset * scale, zOffset * scale, xSize, ySize, zSize);
        for (int partX = 0; partX < scale; partX++) {
            for (int partZ = 0; partZ < scale; partZ++) {
                for (int partY = 0; partY < 32; partY++) {
                    double d = 0.25D;
                    double d1 = undergroundBuffer[((partX) * zSize + (partZ)) * ySize + (partY)];
                    double d2 = undergroundBuffer[((partX) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d3 = undergroundBuffer[((partX + 1) * zSize + (partZ)) * ySize + (partY)];
                    double d4 = undergroundBuffer[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d5 = (undergroundBuffer[((partX) * zSize + (partZ)) * ySize + (partY + 1)] - d1) * d;
                    double d6 = (undergroundBuffer[((partX) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d2) * d;
                    double d7 = (undergroundBuffer[((partX + 1) * zSize + (partZ)) * ySize + (partY + 1)] - d3) * d;
                    double d8 = (undergroundBuffer[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d4) * d;
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
                                for (BlockPos position : positions) {
                                    chunk.setBlockState(position.getX(), position.getY(), position.getZ(), blockState);
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
        int islandHeight = 100, islandSpacerHeight = 128;
        NoiseGeneratorOctaves noiseGenerator = new NoiseGeneratorOctaves(randomizer, 4);
        biomeBuffer = noiseGenerator.generateNoiseOctaves(biomeBuffer, x * 16, z * 16, 0, 16, 16, 1, offset * 2D, offset * 2D, offset * 2D);
        for (int gx = 0; gx < 16; gx++) {
            for (int gz = 0; gz < 16; gz++) {
                Biome biome = abiome[gx + gz * 16];
                int i1 = (int) (biomeBuffer[gx + gz * 16] / 3D + 3D + randomizer.nextDouble() * 0.25D);
                int j1 = -1;
                IBlockState top = biome.topBlock;
                IBlockState filler = biome.fillerBlock;
                for (int islandY = 0; islandY <= this.world.getHeight(); islandY += islandSpacerHeight) {
                    for (int gy = islandY + islandHeight; gy >= islandY; gy--) {
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
                            j1 = randomizer.nextInt(4);
                            filler = Blocks.SANDSTONE.getDefaultState();
                        }
                    }
                }
            }
        }
    }

    /**
     * Generate a noise octave.
     * @author notch
     */
    @SuppressWarnings("DataFlowIssue")
    private double[] generateANoiseOctave(double[] temperatures, int xOffset, int zOffset, int xSize, int ySize, int zSize) {
        if (temperatures == null) {
            temperatures = new double[xSize * ySize * zSize];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        d *= 2D;
        double[] xNoises = xNoiseGenerator.generateNoiseOctaves(null, xOffset, 0, zOffset, xSize, ySize, zSize, d / 80D, d1 / 160D, d / 80D);
        double[] yNoises = yNoiseGenerator.generateNoiseOctaves(null, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
        double[] zNoises = zNoiseGenerator.generateNoiseOctaves(null, xOffset, 0, zOffset, xSize, ySize, zSize, d, d1, d);
        int index = 0;
        for (int x = 0; x < xSize; x++) {
            for (int z = 0; z < zSize; z++) {
                for (int y = 0; y < ySize; y++) {
                    double temperature;
                    double d10 = yNoises[index] / 512D;
                    double d11 = zNoises[index] / 512D;
                    double selector = (xNoises[index] / 10D + 1.0D) / 2D;
                    if (selector < 0.0D) {
                        temperature = d10;
                    } else if (selector > 1.0D) {
                        temperature = d11;
                    } else {
                        temperature = d10 + (d11 - d10) * selector;
                    }
                    temperature -= 8D;
                    int yOffset = 32;
                    if (y > ySize - yOffset) {
                        double fullTemperature = (float) (y - (ySize - yOffset)) / ((float) yOffset - 1.0F);
                        temperature = temperature * (1.0D - fullTemperature) + -30D * fullTemperature;
                    }
                    yOffset = 8;
                    if (y < yOffset) {
                        double partTemperature = (float) (yOffset - y) / ((float) yOffset - 1.0F);
                        temperature = temperature * (1.0D - partTemperature) + -30D * partTemperature;
                    }
                    temperatures[index] = temperature;
                    index++;
                }
            }
        }
        return temperatures;
    }

    /**
     * Generate chunk of dimension.<br>
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        this.randomizer.setSeed((long) chunkX * 0x4f9939f508L + (long) chunkX * 0x1ef1565bd5L);

        ChunkPrimer chunkPrimer = new ChunkPrimer();

        this.generateUnderground(chunkX, chunkZ, chunkPrimer);
        this.generateBiomes(chunkX, chunkZ, chunkPrimer, world.getBiomeProvider().getBiomes(null, chunkX * 16, chunkZ * 16, 16, 16));

        // Generate obsidian platform
        if (chunkX == 0 && chunkZ == 0) {
            IBlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = ((WorldProviderSky) this.world.provider).getBaseHeight() + 127; i < ((WorldProviderSky) this.world.provider).getBaseHeight() + 131; i++) {
                chunkPrimer.setBlockState(0, i, 0, ground);
                chunkPrimer.setBlockState(0, i, 1, ground);
                chunkPrimer.setBlockState(1, i, 0, ground);
                chunkPrimer.setBlockState(1, i, 1, ground);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunkX == -1 && chunkZ == 0) {
            IBlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunkPrimer.setBlockState(15, i, 0, ground);
                chunkPrimer.setBlockState(15, i, 1, ground);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunkX == 0 && chunkZ == -1) {
            IBlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunkPrimer.setBlockState(0, i, 15, ground);
                chunkPrimer.setBlockState(1, i, 15, ground);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunkX == -1 && chunkZ == -1) {
            IBlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunkPrimer.setBlockState(15, i, 15, ground);
                ground = Blocks.AIR.getDefaultState();
            }
        }

        if (this.settings.useCaves) {
            this.caveGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
        }

        if (this.mapFeaturesEnabled) {
            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
            }
            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
            }
            //if (this.settings.useFloatingBoats) {
                this.floatingBoatGenerator.generate(this.world, chunkX, chunkZ, chunkPrimer);
            //}
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

        int chunkWorldX = chunkX * 16;
        int chunkWorldZ = chunkZ * 16;
        BlockPos chunkWorldPosition = new BlockPos(chunkWorldX, ((WorldProviderSky) this.world.provider).getBaseHeight(), chunkWorldZ);
        Biome biome = world.getBiome(chunkWorldPosition.add(16, ((WorldProviderSky) this.world.provider).getBaseHeight(), 16));
        randomizer.setSeed(world.getSeed());
        long xTemperature = (randomizer.nextLong() / 2L) * 2L + 1L;
        long zTemperature = (randomizer.nextLong() / 2L) * 2L + 1L;
        randomizer.setSeed((long) chunkX * xTemperature + (long) chunkZ * zTemperature ^ world.getSeed());
        boolean hasVillage = false;
        ChunkPos chunkPosition = new ChunkPos(chunkWorldX, chunkWorldZ);

        if (this.mapFeaturesEnabled) {
            if (this.settings.useVillages) {
                hasVillage = this.villageGenerator.generateStructure(this.world, this.randomizer, chunkPosition);
            }
            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generateStructure(this.world, this.randomizer, chunkPosition);
            }
            //if (this.settings.useFloatingBoats) {
                this.floatingBoatGenerator.generateStructure(this.world, this.randomizer, chunkPosition);
            //}
        }

        if (biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes && !hasVillage && randomizer.nextInt(this.settings.waterLakeChance) == 0) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(256),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(world, randomizer, chunkWorldPosition.add(x, y, z));
        }
        if (!hasVillage && this.randomizer.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(this.randomizer.nextInt(240) + 8),
                z = this.randomizer.nextInt(16) + 8;
            if (y < this.world.getSeaLevel() || this.randomizer.nextInt(this.settings.lavaLakeChance / 8) == 0) {
                (new WorldGenLakes(Blocks.LAVA)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
            }
        }
        if (this.settings.useDungeons) {
            for (int _times = 0; _times < this.settings.dungeonChance; _times++) {
                int x = this.randomizer.nextInt(16) + 8,
                    y = this.randomizer.nextInt(256),
                    z = this.randomizer.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
            }
        }

        this.decorate(chunkX, chunkZ);
        WorldEntitySpawner.performWorldGenSpawning(this.world, biome, chunkWorldX + 8, chunkWorldZ + 8, 16, 16, this.randomizer);

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos precipitationHeight = this.world.getPrecipitationHeight(chunkWorldPosition.add(x, 0, z));
                if (this.world.canBlockFreezeWater(precipitationHeight.down())) {
                    this.world.setBlockState(precipitationHeight.down(), Blocks.ICE.getDefaultState(), 2);
                }
                if (this.world.canSnowAt(precipitationHeight, true)) {
                    this.world.setBlockState(precipitationHeight, Blocks.SNOW_LAYER.getDefaultState(), 2);
                }
            }
        }

        BlockSand.fallInstantly = false;
    }

    /**
     * Decorate chunk from biome works like {@link net.minecraft.world.biome.Biome#decorate(World, Random, BlockPos)}.
     * @author CoolCLK
     */
    private void decorate(int chunkX, int chunkZ) {
        int chunkWorldX = chunkX * 16, chunkWorldZ = chunkZ * 16;
        BlockPos chunkWorldPosition = new BlockPos(chunkWorldX, ((WorldProviderSky) this.world.provider).getBaseHeight(), chunkWorldZ);
        Biome biome = this.world.getBiome(chunkWorldPosition.add(16, 0, 16));

        for (int _times = 0; _times < 10; _times++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(256),
                z = this.randomizer.nextInt(16);
            (new WorldGenClay(32)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int _times = 0; _times < 20; _times++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(256),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIRT.getDefaultState(), 32)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int k2 = 0; k2 < 10; k2++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(256),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), 32)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int i3 = 0; i3 < 30; i3++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(256),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), 16)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int j3 = 0; j3 < 25; j3++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), 8)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int k3 = 0; k3 < 3; k3++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), 8)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int l3 = 0; l3 < 12; l3++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), 7)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int i4 = 0; i4 < 2; i4++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(64),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), 7)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int i4 = 0; i4 < 1; i4++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(64),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.EMERALD_ORE.getDefaultState(), 7)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int j4 = 0; j4 < 2; j4++) {
            int x = this.randomizer.nextInt(16),
                y = this.randomizer.nextInt(64) + this.randomizer.nextInt(16),
                z = this.randomizer.nextInt(16);
            (new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), 6)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }

        double treesGenerateFactor = 0.5D;
        NoiseGeneratorPerlin[] treesGeneratorCollection = new NoiseGeneratorPerlin[8];
        for (int j = 0; j < 8; j++) {
            treesGeneratorCollection[j] = new NoiseGeneratorPerlin(this.randomizer, 0);
        }
        double treesGenerateTemperature = 0.0D;
        double treesChanceFactor = 1.0D;
        for (int i = 0; i < 8; i++) {
            treesGenerateTemperature += treesGeneratorCollection[i].getValue(((double) chunkWorldX * treesGenerateFactor) * treesChanceFactor, ((double) chunkWorldZ * treesGenerateFactor) * treesChanceFactor) / treesChanceFactor;
            treesChanceFactor /= 2D;
        }
        int treeGenerateOffset = (int) ((treesGenerateTemperature / 8D + this.randomizer.nextDouble() * 4D + 4D) / 3D);
        int treesGenerateChance = 0;
        if (this.randomizer.nextInt(10) == 0) {
            treesGenerateChance++;
        }
        if (biome == Biomes.FOREST) {
            treesGenerateChance += treeGenerateOffset + 5;
        }
        if (biome == Biomes.TAIGA) {
            treesGenerateChance += treeGenerateOffset + 5;
        }
        if (biome == Biomes.DESERT) {
            treesGenerateChance -= 20;
        }
        if (biome == Biomes.ICE_PLAINS) {
            treesGenerateChance -= 20;
        }
        if (biome == Biomes.PLAINS) {
            treesGenerateChance -= 20;
        }
        for (int _times = 0; _times < treesGenerateChance; _times++) {
            int x = this.randomizer.nextInt(16) + 8,
                z = this.randomizer.nextInt(16) + 8;
            WorldGenerator worldgenerator = biome.getRandomTreeFeature(this.randomizer);
            worldgenerator.generate(this.world, this.randomizer, chunkWorldPosition.add(x, this.world.getHeight(x, z), z));
        }
        for (int _times = 0; _times < 2; _times++) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        if (this.randomizer.nextInt(2) == 0) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenFlowers(Blocks.RED_FLOWER, BlockFlower.EnumFlowerType.POPPY)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        if (this.randomizer.nextInt(4) == 0) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.BROWN_MUSHROOM)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        if (this.randomizer.nextInt(8) == 0) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenBush(Blocks.RED_MUSHROOM)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int _times = 0; _times < 10; _times++) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenReed()).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        if (this.randomizer.nextInt(32) == 0) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(128),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenPumpkin()).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }

        if (biome == Biomes.DESERT) {
            for (int _times = 0; _times < 10; _times++) {
                int x = this.randomizer.nextInt(16) + 8,
                    y = this.randomizer.nextInt(128),
                    z = this.randomizer.nextInt(16) + 8;
                (new WorldGenCactus()).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
            }
        }
        for (int _times = 0; _times < 50; _times++) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(this.randomizer.nextInt(120) + 8),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
        for (int _times = 0; _times < 20; _times++) {
            int x = this.randomizer.nextInt(16) + 8,
                y = this.randomizer.nextInt(this.randomizer.nextInt(this.randomizer.nextInt(112) + 8) + 8),
                z = this.randomizer.nextInt(16) + 8;
            (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(this.world, this.randomizer, chunkWorldPosition.add(x, y, z));
        }
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
        return creatureType == EnumCreatureType.CREATURE ||
                creatureType == EnumCreatureType.WATER_CREATURE ||
                creatureType == EnumCreatureType.MONSTER ?
                Collections.singletonList(new Biome.SpawnListEntry(EntityChicken.class, 10, 1, 4)) :
                Collections.emptyList();
    }

    /**
     * Get the nearest structure of dimension.<br>
     * @return The position of the nearest structure, or return null
     * @author CoolCLK
     */
    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World world, @Nonnull String structureName, @Nonnull BlockPos blockPos, boolean findUnexplored) {
        if (this.mapFeaturesEnabled) {
            for (MapGenStructure generator : this.allowedStructureGenerators) {
                if (generator != null && generator.getStructureName().equals(structureName)) {
                    return generator.getNearestStructurePos(world, blockPos, findUnexplored);
                }
            }
        }
        return null;
    }

    /**
     * Recreate structures of dimension.<br>
     * @author CoolCLK
     */
    @Override
    @SuppressWarnings("DataFlowIssue")
    public void recreateStructures(@Nonnull Chunk chunk, int x, int z) {
        if (this.mapFeaturesEnabled) {
            if (this.settings.useStrongholds) {
                this.strongholdGenerator.generate(this.world, x, z, null);
            }
            if (this.settings.useVillages) {
                this.villageGenerator.generate(this.world, x, z, null);
            }
            //if (this.settings.useFloatingBoats) {
                this.floatingBoatGenerator.generate(this.world, x, z, null);
            //}
        }
    }

    /**
     * Check if in the structure of dimension.<br>
     * @author CoolCLK
     */
    @Override
    public boolean isInsideStructure(@Nonnull World world, @Nonnull String structureName, @Nonnull BlockPos blockPos) {
        if (this.mapFeaturesEnabled) {
            for (MapGenStructure generator : this.allowedStructureGenerators) {
                if (generator != null && generator.getStructureName().equals(structureName)) {
                    return generator.isInsideStructure(blockPos);
                }
            }
        }
        return false;
    }
}
