package coolclk.skydimension.forge.world.gen;

import coolclk.skydimension.forge.world.gen.feature.structure.Feature;
import coolclk.skydimension.forge.world.gen.feature.structure.FloatingShipStructure;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

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
public class SkyChunkGenerator extends ChunkGenerator<SkyGenerationSettings> {
    private final OctavesNoiseGenerator xNoiseGenerator;
    private final OctavesNoiseGenerator yNoiseGenerator;
    private final OctavesNoiseGenerator zNoiseGenerator;
    private final Random randomizer;
    private final World world;
    private double[] undergroundBuffer;
    private final boolean mapFeaturesEnabled;
    private final List<? extends Structure<?>> structures;

    /**
     * THe function will be involved when sky dimension started to generate.<br>
     * @author CoolCLK
     */
    public SkyChunkGenerator(World world, BiomeProvider provider, SkyGenerationSettings settings) {
        super(world, provider, settings);
        this.world = world;
        this.mapFeaturesEnabled = world.getWorldInfo().isMapFeaturesEnabled();
        this.randomizer = new Random(world.getSeed());
        this.structures = Arrays.asList(net.minecraft.world.gen.feature.Feature.VILLAGE, Feature.STRONGHOLD, Feature.FLOATING_SHIP);

        this.xNoiseGenerator = new OctavesNoiseGenerator(this.randomizer, 8);
        this.yNoiseGenerator = new OctavesNoiseGenerator(this.randomizer, 16);
        this.zNoiseGenerator = new OctavesNoiseGenerator(this.randomizer, 16);
    }

    /**
     * Generate underground part of dimension.
     * @author CoolCLK
     */
    @Override
    public void makeBase(@Nonnull IWorld worldIn, IChunk chunk) {
        byte scale = 2;
        int xSize = scale + 1;
        byte ySize = 33;
        int zSize = scale + 1;
        this.undergroundBuffer = generateANoiseOctave(this.undergroundBuffer, chunk.getPos().x * scale, chunk.getPos().z * scale, xSize, ySize, zSize);
        for (int partX = 0; partX < scale; partX++) {
            for (int partZ = 0; partZ < scale; partZ++) {
                for (int partY = 0; partY < 32; partY++) {
                    double d = 0.25D;
                    double d1 = this.undergroundBuffer[((partX) * zSize + (partZ)) * ySize + (partY)];
                    double d2 = this.undergroundBuffer[((partX) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d3 = this.undergroundBuffer[((partX + 1) * zSize + (partZ)) * ySize + (partY)];
                    double d4 = this.undergroundBuffer[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY)];
                    double d5 = (this.undergroundBuffer[((partX) * zSize + (partZ)) * ySize + (partY + 1)] - d1) * d;
                    double d6 = (this.undergroundBuffer[((partX) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d2) * d;
                    double d7 = (this.undergroundBuffer[((partX + 1) * zSize + (partZ)) * ySize + (partY + 1)] - d3) * d;
                    double d8 = (this.undergroundBuffer[((partX + 1) * zSize + (partZ + 1)) * ySize + (partY + 1)] - d4) * d;
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
                                BlockState blockState = Blocks.AIR.getDefaultState();
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
                                    chunk.setBlockState(new BlockPos(position.getX(), position.getY(), position.getZ()), blockState, false);
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

    public void buildPlatform(IChunk chunk) {
        if (chunk.getPos().equals(new ChunkPos(0, 0))) {
            BlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = this.getBaseHeight(chunk.getPos()) + 127; i < this.getBaseHeight(chunk.getPos()) + 131; i++) {
                chunk.setBlockState(new BlockPos(0, i, 0), ground, false);
                chunk.setBlockState(new BlockPos(0, i, 1), ground, false);
                chunk.setBlockState(new BlockPos(1, i, 0), ground, false);
                chunk.setBlockState(new BlockPos(1, i, 1), ground, false);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunk.getPos().equals(new ChunkPos(-1, 0))) {
            BlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunk.setBlockState(new BlockPos(15, i, 0), ground, false);
                chunk.setBlockState(new BlockPos(15, i, 1), ground, false);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunk.getPos().equals(new ChunkPos(0, -1))) {
            BlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunk.setBlockState(new BlockPos(0, i, 15), ground, false);
                chunk.setBlockState(new BlockPos(1, i, 15), ground, false);
                ground = Blocks.AIR.getDefaultState();
            }
        } else if (chunk.getPos().equals(new ChunkPos(-1, -1))) {
            BlockState ground = Blocks.OBSIDIAN.getDefaultState();
            for (int i = 127; i < 131; i++) {
                chunk.setBlockState(new BlockPos(15, i, 15), ground, false);
                ground = Blocks.AIR.getDefaultState();
            }
        }
    }

    /**
     * Generate biomes part of dimension.<br>
     * @author CoolCLK
     */
    @Override
    public void generateSurface(@Nonnull IChunk chunk) {
        this.buildPlatform(chunk);

        double offset = 0.03125D;
        int islandHeight = 100, islandSpacerHeight = 128;
        OctavesNoiseGenerator noiseGenerator = new OctavesNoiseGenerator(this.randomizer, 4);
        for (int gx = 0; gx < 16; gx++) {
            for (int gz = 0; gz < 16; gz++) {
                Biome biome = this.getBiomeProvider().getBiomes(chunk.getPos().x * 16, chunk.getPos().z * 16, 16, 16, true)[gx + gz * 16];
                int blockTemperature = (int) (noiseGenerator.func_215462_a(chunk.getPos().x * 16, 0, chunk.getPos().z * 16, offset * 2D, offset * 2D, true)/*biomeBuffer[gx + gz * 16]*/ / 3D + 3D + randomizer.nextDouble() * 0.25D);
                int lastBlockState = -1;
                BlockState top = biome.getSurfaceBuilderConfig().getTop();
                BlockState filler = biome.getSurfaceBuilderConfig().getUnder();
                for (int islandY = 0; islandY <= this.world.getHeight(); islandY += islandSpacerHeight) {
                    for (int gy = islandY + islandHeight; gy >= islandY; gy--) {
                        BlockState state = chunk.getBlockState(new BlockPos(gx, gy, gz));
                        if (state.getBlock() == Blocks.AIR) {
                            lastBlockState = -1;
                            continue;
                        }
                        if (state.getBlock() != Blocks.STONE) {
                            continue;
                        }
                        if (lastBlockState == -1) {
                            if (blockTemperature <= 0) {
                                top = Blocks.AIR.getDefaultState();
                                filler = Blocks.STONE.getDefaultState();
                            }
                            lastBlockState = blockTemperature;
                            chunk.setBlockState(new BlockPos(gx, gy, gz), top, false);
                            continue;
                        }
                        if (lastBlockState <= 0) {
                            continue;
                        }
                        lastBlockState--;
                        chunk.setBlockState(new BlockPos(gx, gy, gz), filler, false);
                        if (lastBlockState == 0 && filler.getBlock() == Blocks.SAND) {
                            lastBlockState = randomizer.nextInt(4);
                            filler = Blocks.SANDSTONE.getDefaultState();
                        }
                    }
                }
            }
        }
    }

    /**
     * Generate a noise octave.
     * @author CoolCLK
     */
    private double[] generateANoiseOctave(double[] temperatures, int xOffset, int zOffset, int xSize, int ySize, int zSize) {
        if (temperatures == null) {
            temperatures = new double[xSize * ySize * zSize];
        }
        double d = 684.41200000000003D;
        double d1 = 684.41200000000003D;
        d *= 2D;
        int index = 0;
        for (int x = 0; x < xSize; x++) {
            for (int z = 0; z < zSize; z++) {
                for (int y = 0; y < ySize; y++) {
                    double temperature;
                    double d10 = yNoiseGenerator.func_215462_a(xOffset, 0, zOffset, d, d1, true) / 512D;
                    double d11 = zNoiseGenerator.func_215462_a(xOffset, 0, zOffset, d, d1, true) / 512D;
                    double selector = (xNoiseGenerator.func_215462_a(xOffset, 0, zOffset, d / 80D, d1 / 160D, true) / 10D + 1.0D) / 2D;
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
     * Decorate chunk.
     * @author CoolCLK
     */
    @Override
    public void decorate(@Nonnull WorldGenRegion region) {
        this.biomeProvider.getBiome(region.getMainChunkX() * 16 + 8, region.getMainChunkZ() * 16 + 8).addStructure(new FloatingShipStructure(NoFeatureConfig::deserialize), IFeatureConfig.NO_FEATURE_CONFIG);
        super.decorate(region);
    }

    /**
     * Get spawn entities of dimension.<br>
     * @return The possible spawns of the biome
     * @author CoolCLK
     */
    @Nonnull
    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EntityClassification classification, @Nonnull BlockPos position) {
        return classification != EntityClassification.MONSTER && classification != EntityClassification.AMBIENT ? this.world.getBiome(position).getSpawns(classification) : Collections.emptyList();
    }

    /**
     * Get the nearest structure of dimension.<br>
     * @return The position of the nearest structure, or return null
     * @author CoolCLK
     */
    @Nullable
    @Override
    public BlockPos findNearestStructure(@Nonnull World world, @Nonnull String structureName, @Nonnull BlockPos blockPos, int distance, boolean findUnexplored) {
        if (this.mapFeaturesEnabled) {
            for (Structure<?> structure : this.structures) {
                if (structure != null && structure.getStructureName().equals(structureName)) {
                    return structure.findNearest(world, this, blockPos, distance, findUnexplored);
                }
            }
        }
        return null;
    }

    /**
     * Ready for higher version of Minecraft.
     * @author CoolCLK
     */
    public int getBaseHeight(@SuppressWarnings("unused") ChunkPos chunkCoordinate) {
        return 0;
    }

    @Override
    public int func_222529_a(int chunkX, int chunkZ, @Nonnull Heightmap.Type type) {
        switch (type) {
            case OCEAN_FLOOR:
            case OCEAN_FLOOR_WG:{
                return 0;
            }
            case WORLD_SURFACE:
            case WORLD_SURFACE_WG: {
                return 224;
            }
            case MOTION_BLOCKING:
            case MOTION_BLOCKING_NO_LEAVES: {
                return 232;
            }
        }
        return -1;
    }

    @Override
    public int getGroundHeight() {
        return this.world.getSeaLevel() + 1;
    }

    /**
     * Make some structures specially.
     * @author CoolCLK
     */
    @Override
    public boolean hasStructure(@Nonnull Biome biome, Structure<? extends IFeatureConfig> structure) {
        if ("FloatingShip".equals(structure.getStructureName())) {
            return true;
        }
        return super.hasStructure(biome, structure);
    }
}
