package coolclk.skydimension.forge.world.gen.structure;

import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;

import javax.annotation.Nonnull;
import java.util.Random;

public class MapGenStrongholdPortalRoom extends MapGenStronghold {
    @Nonnull
    @Override
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
        public Start() {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ) {
            super(chunkX, chunkZ);
            StructureStrongholdPieces.prepareStructurePieces();
            StructureStrongholdPieces.PortalRoom portalRoom = new StructureStrongholdPieces.PortalRoom(
                    0,
                    random,
                    new StructureBoundingBox((chunkX << 4) + 2, 64, (chunkZ << 4) + 2, (chunkX << 4) + 2 + 5 - 1, 74, (chunkZ << 4) + 2 + 5 - 1),
                    EnumFacing.NORTH
            );
            this.components.add(portalRoom);
            portalRoom.buildComponent(null, this.components, random);
            this.updateBoundingBox();
            this.markAvailableHeight(worldIn, random, 10);
        }
    }
}
