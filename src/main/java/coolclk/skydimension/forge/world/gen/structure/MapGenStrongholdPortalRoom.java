package coolclk.skydimension.forge.world.gen.structure;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.*;

import javax.annotation.Nonnull;
import java.util.Random;

public class MapGenStrongholdPortalRoom extends MapGenStronghold {
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
        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            StructureStrongholdPieces.prepareStructurePieces();
            StructureStrongholdPieces.Stairs2 stairs2 = new StructureStrongholdPieces.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);

            StructureStrongholdPieces.PortalRoom portalRoom = new StructureStrongholdPieces.PortalRoom(
                    0,
                    random,
                    new StructureBoundingBox((chunkX << 4) + 2, 64, (chunkZ << 4) + 2, (chunkX << 4) + 6, 74, (chunkZ << 4) + 6),
                    EnumFacing.NORTH
            );
            this.components.add(portalRoom);
            portalRoom.buildComponent(stairs2, this.components, random);

            this.updateBoundingBox();
            this.markAvailableHeight(worldIn, random, 0);
        }
    }
}
