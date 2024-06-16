package coolclk.skydimension.forge.world.biome.provider;

import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SkyBiomeProvider extends BiomeProvider {
    private final Layer genBiomes;
    private final Layer biomeFactoryLayer;
    private final Biome[] biomes;

    public SkyBiomeProvider(SkyBiomeProviderSettings settings) {
        this.biomes = new Biome[]{Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU};
        Layer[] layers = LayerUtil.buildOverworldProcedure(settings.getWorldInfo().getSeed(), settings.getWorldInfo().getGenerator(), settings.getGeneratorSettings());
        this.genBiomes = layers[0];
        this.biomeFactoryLayer = layers[1];
    }

    @Nonnull
    @Override
    public Biome getBiome(int x, int z) {
        return this.biomeFactoryLayer.func_215738_a(x, z);
    }

    @Nonnull
    @Override
    public Biome func_222366_b(int x, int z) {
        return this.genBiomes.func_215738_a(x, z);
    }

    @Nonnull
    @Override
    public Biome[] getBiomes(int x, int z, int xSize, int zSize, boolean unexplored) {
        return this.biomeFactoryLayer.generateBiomes(x, z, xSize, zSize);
    }

    @Nonnull
    @Override
    public Set<Biome> getBiomesInSquare(int x, int z, int size) {
        int xStart = x - size >> 2;
        int zStart = z - size >> 2;
        int xEnd = x + size >> 2;
        int zEnd = z + size >> 2;
        int xSize = xEnd - xStart + 1;
        int zSize = zEnd - zStart + 1;
        Set<Biome> biomes = Sets.newHashSet();
        Collections.addAll(biomes, this.genBiomes.generateBiomes(xStart, zStart, xSize, zSize));
        return biomes;
    }

    @Nullable
    public BlockPos findBiomePosition(int x, int z, int size, @Nonnull List<Biome> biomes, @Nonnull Random random) {
        int xStart = x - size >> 2;
        int zStart = z - size >> 2;
        int xEnd = x + size >> 2;
        int zEnd = z + size >> 2;
        int xSize = xEnd - xStart + 1;
        int zSize = zEnd - zStart + 1;
        Biome[] atBiomes = this.genBiomes.generateBiomes(xStart, zStart, xSize, zSize);
        BlockPos biomePosition = null;
        int hitTimes = 0;
        for (int i = 0; i < xSize * zSize; i++) {
            int _x = xStart + i % xSize << 2;
            int _z = zStart + i / xSize << 2;
            if (biomes.contains(atBiomes[i])) {
                if (biomePosition == null || random.nextInt(hitTimes + 1) == 0) {
                    biomePosition = new BlockPos(_x, 0, _z);
                }
                hitTimes++;
            }
        }
        return biomePosition;
    }

    @Override
    public boolean hasStructure(@Nonnull Structure<?> structure) {
        return this.hasStructureCache.computeIfAbsent(structure, (p_205006_1_) -> {
            for (Biome biome : this.biomes) {
                if (biome.hasStructure(p_205006_1_)) {
                    return true;
                }
            }
            return false;
        });
    }

    @Nonnull
    @Override
    public Set<BlockState> getSurfaceBlocks() {
        if (this.topBlocksCache.isEmpty()) {
            for (Biome biome : this.biomes) {
                this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
            }
        }
        return this.topBlocksCache;
    }
}
