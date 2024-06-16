package coolclk.skydimension.forge.world.gen.feature.structure;

import com.mojang.datafixers.Dynamic;
import coolclk.skydimension.forge.world.gen.SkyGenerationSettings;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Function;

public class FloatingShipStructure extends Structure<NoFeatureConfig> {
    public FloatingShipStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> function) {
        super(function);
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> baseChunkGenerator, @Nonnull Random random, int chunkX, int chunkZ, int distanceScaleX, int distanceScaleZ) {
        if (baseChunkGenerator.getSettings() instanceof SkyGenerationSettings) {
            ChunkGenerator<? extends SkyGenerationSettings> chunkGenerator = (ChunkGenerator<? extends SkyGenerationSettings>) baseChunkGenerator;
            int distance = chunkGenerator.getSettings().getFloatingShipDistance();
            int separation = chunkGenerator.getSettings().getFloatingShipSeparation();
            int lvt_9_1_ = chunkX + distance * distanceScaleX;
            int lvt_10_1_ = chunkZ + distance * distanceScaleZ;
            int lvt_11_1_ = lvt_9_1_ < 0 ? lvt_9_1_ - distance + 1 : lvt_9_1_;
            int lvt_12_1_ = lvt_10_1_ < 0 ? lvt_10_1_ - distance + 1 : lvt_10_1_;
            int templateChunkX = lvt_11_1_ / distance;
            int templateChunkZ = lvt_12_1_ / distance;
            ((SharedSeedRandom)random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), templateChunkX, templateChunkZ, 10387313);
            templateChunkX *= distance;
            templateChunkZ *= distance;
            templateChunkX += (random.nextInt(distance - separation) + random.nextInt(distance - separation)) / 2;
            templateChunkZ += (random.nextInt(distance - separation) + random.nextInt(distance - separation)) / 2;
            return new ChunkPos(templateChunkX, templateChunkZ);
        }
        throw new IllegalArgumentException("Unsupported chunk generator: " + baseChunkGenerator.getClass());
    }

    @Override
    public boolean hasStartAt(@Nonnull ChunkGenerator<?> chunkGenerator, @Nonnull Random random, int chunkX, int chunkZ) {
        if (!(chunkGenerator.getSettings() instanceof SkyGenerationSettings)) {
            return false;
        }
        ChunkPos chunkCoordinate = this.getStartPositionForPosition(chunkGenerator, random, chunkX, chunkZ, 0, 0);
        if (chunkX == chunkCoordinate.x && chunkZ == chunkCoordinate.z) {
            Biome biome = chunkGenerator.getBiomeProvider().getBiome(new BlockPos((chunkX << 4) + 9, 0, (chunkZ << 4) + 9));
            if (!chunkGenerator.hasStructure(biome, Feature.FLOATING_SHIP)) {
                return false;
            } else {
                int templateY = getYPosForStructure(chunkX, chunkZ, chunkGenerator);
                return templateY >= 60;
            }
        } else {
            return false;
        }
    }

    @Nonnull
    @Override
    public IStartFactory getStartFactory() {
        return Start::new;
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return "FloatingShip";
    }

    @Override
    public int getSize() {
        return 8;
    }

    private static int getYPosForStructure(int chunkX, int chunkZ, ChunkGenerator<?> chunkGenerator) {
        Random randomizer = new Random(chunkX + chunkZ * 10387313L);
        Rotation rotation = Rotation.values()[randomizer.nextInt(Rotation.values().length)];
        int startX = 5;
        int startZ = 5;
        if (rotation == Rotation.CLOCKWISE_90) {
            startX = -5;
        } else if (rotation == Rotation.CLOCKWISE_180) {
            startX = -5;
            startZ = -5;
        } else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
            startZ = -5;
        }
        int x = (chunkX << 4) + 7;
        int z = (chunkZ << 4) + 7;
        int endY = chunkGenerator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        int zStartY = chunkGenerator.func_222531_c(x, z + startZ, Heightmap.Type.WORLD_SURFACE_WG);
        int xStartY = chunkGenerator.func_222531_c(x + startX, z, Heightmap.Type.WORLD_SURFACE_WG);
        int startY = chunkGenerator.func_222531_c(x + startX, z + startZ, Heightmap.Type.WORLD_SURFACE_WG);
        return Math.min(Math.min(endY, zStartY), Math.min(xStartY, startY));
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> structure, int chunkX, int chunkZ, Biome biome, MutableBoundingBox boundingBox, int references, long seed) {
            super(structure, chunkX, chunkZ, biome, boundingBox, references, seed);
        }

        @Override
        public void init(@Nonnull ChunkGenerator<?> chunkGenerator, @Nonnull TemplateManager templateManager, int chunkX, int chunkZ, @Nonnull Biome biome) {
            Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
            int templateY = getYPosForStructure(chunkX, chunkZ, chunkGenerator);
            if (templateY >= 60) {
                BlockPos templatePosition = new BlockPos(chunkX * 16 + 8, templateY, chunkZ * 16 + 8);
                FloatingShipPieces.Piece component = new FloatingShipPieces.Piece(templateManager, templatePosition, rotation, true);
                this.components.add(component);
                this.recalculateStructureSize();
            }
        }
    }
}
