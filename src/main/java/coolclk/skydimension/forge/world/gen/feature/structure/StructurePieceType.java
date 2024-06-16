package coolclk.skydimension.forge.world.gen.feature.structure;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.ShipwreckPieces;

import static net.minecraft.world.gen.feature.structure.IStructurePieceType.register;

public class StructurePieceType {
    public static final IStructurePieceType FLOATING_SHIP = register(ShipwreckPieces.Piece::new, "FloatingShip");
}
