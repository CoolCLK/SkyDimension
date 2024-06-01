package coolclk.skydimension.forge.world.gen.structure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * The generator of floating boat structure.
 * @author CoolCLK
 */
public class MapGenFloatingBoat extends MapGenStructure {
    private static final int allowedHeight = 60;

    @Nonnull
    @Override
    public String getStructureName() {
        return "FloatingBoat";
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

        if (chunkX < 0) {
            chunkX -= 19;
        }

        if (chunkZ < 0) {
            chunkZ -= 19;
        }

        int areaX = chunkX / 20;
        int areaZ = chunkZ / 20;
        Random random = this.world.setRandomSeed(areaX, areaZ, 10387313);
        areaX = areaX * 20;
        areaZ = areaZ * 20;
        areaX = areaX + (random.nextInt(9) + random.nextInt(9)) / 2;
        areaZ = areaZ + (random.nextInt(9) + random.nextInt(9)) / 2;

        if (firstChunkX == areaX && firstChunkZ == areaZ) {
            int y = getYPosForStructure(firstChunkX, firstChunkZ);
            return y >= allowedHeight;
        }
        return false;
    }

    @Nonnull
    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new Start(this.world, this.rand, chunkX, chunkZ);
    }

    private static int getYPosForStructure(int chunkX, int chunkZ) {
        Random random = new Random(chunkX + chunkZ * 10387313L);
        Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
        ChunkPrimer chunkprimer = new ChunkPrimer();
        int i = 5;
        int j = 5;

        switch (rotation) {
            case CLOCKWISE_90: {
                i = -5;
                break;
            }
            case CLOCKWISE_180: {
                i = -5;
                j = -5;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                j = -5;
                break;
            }
        }

        int k = chunkprimer.findGroundBlockIdx(7, 7);
        int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
        int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
        int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
        return Math.min(Math.min(k, l), Math.min(i1, j1));
    }

    public static class Start extends StructureStart {
        @SuppressWarnings("unused")
        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            this.create(worldIn, random, chunkX, chunkZ);
        }

        private void create(World worldIn, @SuppressWarnings("unused") Random rand, int chunkX, int chunkZ) {
            Random random = new Random(chunkX + chunkZ * 10387313L);
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
            int i = getYPosForStructure(chunkX, chunkZ);
            if (i >= allowedHeight) {
                BlockPos blockpos = new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8);
                this.components.add(new StructureFloatingBoatPieces.FloatingBoat(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation));
                this.updateBoundingBox();
            }
        }
    }
}
