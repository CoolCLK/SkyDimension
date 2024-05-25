package coolclk.skydimension.forge.world.gen.structure;

import static net.minecraft.world.gen.structure.MapGenStructureIO.registerStructure;

public class MapGenStructureHandler {
    static {
        registerStructure(MapGenStrongholdPortalRoom.Start.class, "StrongholdPortalRoom");
    }
}
