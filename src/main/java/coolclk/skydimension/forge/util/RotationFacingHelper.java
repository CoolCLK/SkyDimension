package coolclk.skydimension.forge.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;

public class RotationFacingHelper {
    public static EnumFacing fromRotation(Rotation rotation) {
        switch (rotation) {
            case CLOCKWISE_90: {
                return EnumFacing.WEST;
            }
            case CLOCKWISE_180: {
                return EnumFacing.NORTH;
            }
            case COUNTERCLOCKWISE_90: {
                return EnumFacing.EAST;
            }
        }
        return EnumFacing.SOUTH;
    }
}
