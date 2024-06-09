package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.IObject;
import coolclk.skydimension.forge.world.WorldProviderSky;
import coolclk.skydimension.forge.world.gen.ChunkGeneratorSky;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * The generator of floating ship structure.
 * @author CoolCLK
 */
public class MapGenFloatingShip extends MapGenStructure implements IObject {
    private static final int baseHeight = 120, heightRange = 30;

    public static String getName() {
        return "FloatingShip";
    }

    @Nonnull
    @Override
    public String getStructureName() {
        return getName();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(@Nonnull World worldIn, @Nonnull BlockPos pos, boolean findUnexplored) {
        return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        int firstChunkX = chunkX;
        int firstChunkZ = chunkZ;

        if (chunkX < 0) chunkX -= 19;
        if (chunkZ < 0)chunkZ -= 19;

        int areaX = chunkX / 20;
        int areaZ = chunkZ / 20;
        Random random = this.world.setRandomSeed(areaX, areaZ, 10387313);
        areaX = areaX * 20;
        areaZ = areaZ * 20;
        areaX = areaX + (random.nextInt(9) + random.nextInt(9)) / 2;
        areaZ = areaZ + (random.nextInt(9) + random.nextInt(9)) / 2;
        return firstChunkX == areaX && firstChunkZ == areaZ;
    }

    @Nonnull
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(this.world, this.rand, chunkX, chunkZ);
    }

    private static int getYPosForStructure(World world, int chunkX, int chunkZ) {
        if (world.provider instanceof WorldProviderSky) {
            return ((ChunkGeneratorSky) world.provider.createChunkGenerator()).getBaseHeight(chunkX, chunkZ) + baseHeight + new Random(chunkX + chunkZ * 10387313L).nextInt(heightRange) - (heightRange / 2);
        }
        return -1;
    }

    public static class Start extends StructureStart {
        private boolean shipCreated = false;

        @SuppressWarnings("unused")
        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            this.create(worldIn, random, chunkX, chunkZ);
        }

        private void create(World worldIn, Random random, int chunkX, int chunkZ) {
            random.setSeed(chunkX + chunkZ * 10387313L);
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            int i = getYPosForStructure(worldIn, chunkX, chunkZ);
            BlockPos templatePosition = new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8);
            StructureFloatingShipPieces.FloatingShip component = new StructureFloatingShipPieces.FloatingShip(worldIn, templatePosition, rotation);
            component.setComponentType(random.nextInt());
            this.components.add(component);
            this.updateBoundingBox();
            this.shipCreated = true;
        }

        @Override
        public void generateStructure(@Nonnull World worldIn, @Nonnull Random random, @Nonnull StructureBoundingBox structureBoundingBox) {
            if (!this.shipCreated) {
                this.components.clear();
                this.create(worldIn, random, this.getChunkPosX(), this.getChunkPosZ());
            }
            super.generateStructure(worldIn, random, this.boundingBox);
        }
    }
}
