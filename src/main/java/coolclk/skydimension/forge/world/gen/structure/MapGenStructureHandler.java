package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.IObject;

import static net.minecraft.world.gen.structure.MapGenStructureIO.registerStructure;

public class MapGenStructureHandler implements IObject {
    static {
        registerStructure(MapGenStrongholdPortalRoom.Start.class, "StrongholdPortalRoom");
        registerStructure(MapGenFloatingShip.Start.class, "FloatingBoat");
        StructureFloatingShipPieces.registerPieces();
    }
}
