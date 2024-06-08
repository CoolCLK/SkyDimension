package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.IObject;
import coolclk.skydimension.forge.world.WorldProviderSky;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import java.util.Random;

public class MapGenStrongholdPortalRoom extends MapGenStronghold implements IObject {
    private static final int baseHeight = 60, heightRange = 30;

    private static int getYPosForStructure(World world, int chunkX, int chunkZ) {
        if (world.provider instanceof WorldProviderSky) {
            return ((WorldProviderSky) world.provider).getBaseHeight() + baseHeight + new Random(chunkX + chunkZ * 10387313L).nextInt(heightRange) - (heightRange / 2);
        }
        return -1;
    }

    /**
     * Get a start.
     * @author CoolCLK
     */
    @Nonnull
    @Override
    @SuppressWarnings("StatementWithEmptyBody")
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        Start start;
        for (start = new Start(this.world, this.rand, chunkX, chunkZ);
             start.getComponents().isEmpty();
             start = new Start(this.world, this.rand, chunkX, chunkZ)
        );
        return start;
    }


    public static class Start extends StructureStart {
        /**
         * For extends.
         * @author CoolCLK
         */
        @SuppressWarnings("unused")
        public Start() {
        }

        /**
         * Create a new start.
         * @author CoolCLK
         */
        @SuppressWarnings("DataFlowIssue")
        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            StructureStrongholdPieces.prepareStructurePieces();
            EnumFacing facing = EnumFacing.HORIZONTALS[random.nextInt(4)];
            StructureStrongholdPieces.PortalRoom portalRoom = new StructureStrongholdPieces.PortalRoom(
                    0,
                    random,
                    StructureBoundingBox.getComponentToAddBoundingBox((chunkX << 4) + 2, getYPosForStructure(worldIn, chunkX, chunkZ), (chunkZ << 4) + 6, -4, -1, 0, 11, 8, 16, facing),
                    facing
            );
            this.components.add(portalRoom);
            portalRoom.buildComponent(null, this.components, random);
            this.updateBoundingBox();
            this.markAvailableHeight(worldIn, random, 0);
        }
    }
}
