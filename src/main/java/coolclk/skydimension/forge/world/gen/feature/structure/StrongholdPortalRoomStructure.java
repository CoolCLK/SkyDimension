package coolclk.skydimension.forge.world.gen.feature.structure;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class StrongholdPortalRoomStructure extends Structure<NoFeatureConfig> {
    private boolean ranBiomeCheck;
    private ChunkPos[] structureCoordinate;
    private final List<StructureStart> structureStarts = Lists.newArrayList();
    private long seed;

    public StrongholdPortalRoomStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean hasStartAt(ChunkGenerator<?> chunkGenerator, @Nonnull Random random, int chunkX, int chunkZ) {
        if (this.seed != chunkGenerator.getSeed()) {
            this.resetData();
        }

        if (!this.ranBiomeCheck) {
            this.reinitializeData(chunkGenerator);
            this.ranBiomeCheck = true;
        }

        for (ChunkPos chunkCoordinate : this.structureCoordinate) {
            if (chunkX == chunkCoordinate.x && chunkZ == chunkCoordinate.z) {
                return true;
            }
        }

        return false;
    }

    private void resetData() {
        this.ranBiomeCheck = false;
        this.structureCoordinate = null;
        this.structureStarts.clear();
    }

    @Nonnull
    @Override
    public Structure.IStartFactory getStartFactory() {
        return Start::new;
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return "Stronghold";
    }

    @Override
    public int getSize() {
        return 8;
    }

    @Nullable
    public BlockPos findNearest(@Nonnull World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, @Nonnull BlockPos position, int distance, boolean findUnexplored) {
        if (!chunkGenerator.getBiomeProvider().hasStructure(this)) {
            return null;
        } else {
            if (this.seed != worldIn.getSeed()) {
                this.resetData();
            }

            if (!this.ranBiomeCheck) {
                this.reinitializeData(chunkGenerator);
                this.ranBiomeCheck = true;
            }

            BlockPos finalPosition = null;
            BlockPos.MutableBlockPos lvt_7_1_ = new BlockPos.MutableBlockPos();
            double nearestDistance = Double.MAX_VALUE;

            for (ChunkPos lvt_13_1_ : this.structureCoordinate) {
                lvt_7_1_.setPos((lvt_13_1_.x << 4) + 8, 32, (lvt_13_1_.z << 4) + 8);
                double distanceToPosition = lvt_7_1_.distanceSq(position);
                if (finalPosition == null) {
                    finalPosition = new BlockPos(lvt_7_1_);
                    nearestDistance = distanceToPosition;
                } else if (distanceToPosition < nearestDistance) {
                    finalPosition = new BlockPos(lvt_7_1_);
                    nearestDistance = distanceToPosition;
                }
            }

            return finalPosition;
        }
    }

    @SuppressWarnings("deprecation")
    private void reinitializeData(ChunkGenerator<?> chunkGenerator) {
        this.seed = chunkGenerator.getSeed();
        List<Biome> includingBiomes = Lists.newArrayList();
        for (Biome biome : Registry.BIOME) {
            if (biome != null && chunkGenerator.hasStructure(biome, Feature.STRONGHOLD)) {
                includingBiomes.add(biome);
            }
        }
        int distance = chunkGenerator.getSettings().getStrongholdDistance();
        int count = chunkGenerator.getSettings().getStrongholdCount();
        int spread = chunkGenerator.getSettings().getStrongholdSpread();
        this.structureCoordinate = new ChunkPos[count];
        int coordinateIndex = 0;
        for (StructureStart start : this.structureStarts) {
            if (coordinateIndex < this.structureCoordinate.length) {
                this.structureCoordinate[coordinateIndex++] = new ChunkPos(start.getChunkPosX(), start.getChunkPosZ());
            }
        }

        Random randomizer = new Random();
        randomizer.setSeed(chunkGenerator.getSeed());
        double angle = randomizer.nextDouble() * Math.PI * 2.0;
        int coordinateCount = coordinateIndex;
        if (coordinateCount < this.structureCoordinate.length) {
            int croodinateIndex = 0;
            int spreadCoordinateIndex = 0;

            for(int i = 0; i < this.structureCoordinate.length; i++) {
                double far = (double) (4 * distance + distance * spreadCoordinateIndex * 6) + (randomizer.nextDouble() - 0.5) * (double) distance * 2.5;
                int horizontalFar = (int) Math.round(Math.cos(angle) * far);
                int vertical = (int) Math.round(Math.sin(angle) * far);
                BlockPos biomePosition = chunkGenerator.getBiomeProvider().findBiomePosition((horizontalFar << 4) + 8, (vertical << 4) + 8, 112, includingBiomes, randomizer);
                if (biomePosition != null) {
                    horizontalFar = biomePosition.getX() >> 4;
                    vertical = biomePosition.getZ() >> 4;
                }

                if (i >= coordinateCount) {
                    this.structureCoordinate[i] = new ChunkPos(horizontalFar, vertical);
                }

                angle += 6.283185307179586 / (double) spread;
                croodinateIndex++;
                if (croodinateIndex == spread) {
                    ++spreadCoordinateIndex;
                    croodinateIndex = 0;
                    spread += 2 * spread / (spreadCoordinateIndex + 1);
                    spread = Math.min(spread, this.structureCoordinate.length - i);
                    angle += randomizer.nextDouble() * Math.PI * 2.0;
                }
            }
        }
    }

    public static class Start extends StructureStart {
        public Start(Structure<?> p_i50780_1_, int p_i50780_2_, int p_i50780_3_, Biome p_i50780_4_, MutableBoundingBox p_i50780_5_, int p_i50780_6_, long p_i50780_7_) {
            super(p_i50780_1_, p_i50780_2_, p_i50780_3_, p_i50780_4_, p_i50780_5_, p_i50780_6_, p_i50780_7_);
        }

        @SuppressWarnings("DataFlowIssue")
        public void init(ChunkGenerator<?> generator, @Nonnull TemplateManager templateManager, int chunkX, int chunkZ, @Nonnull Biome biome) {
            long seed = generator.getSeed();
            this.components.clear();
            this.bounds = MutableBoundingBox.getNewBoundingBox();
            this.rand.setLargeFeatureSeed(seed + 1, chunkX, chunkZ);
            StrongholdPieces.prepareStructurePieces();
            Direction direction = Direction.byIndex(2 + this.rand.nextInt(3));
            int x = (chunkX << 4) + 2, y = 64, z = (chunkZ << 4) + 2;
            StrongholdPieces.PortalRoom portalRoom = new StrongholdPieces.PortalRoom(0, MutableBoundingBox.getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 11, 8, 16, direction), direction);
            this.components.add(portalRoom);
            portalRoom.buildComponent(null, this.components, this.rand);
            this.recalculateStructureSize();
            this.func_214628_a(generator.getSeaLevel(), this.rand, 10);
            ((StrongholdPortalRoomStructure) this.getStructure()).structureStarts.add(this);
        }
    }
}
