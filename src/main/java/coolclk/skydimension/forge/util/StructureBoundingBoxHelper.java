package coolclk.skydimension.forge.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

@SuppressWarnings("unused")
public class StructureBoundingBoxHelper {
    /**
     * Fast {@link net.minecraft.world.gen.structure.StructureBoundingBox#getComponentToAddBoundingBox(int, int, int, int, int, int, int, int, int, EnumFacing)}.
     * @author CoolCLK
     */
    public static StructureBoundingBox getAddedBoundingBox(Vec3i position, StructureBoundingBox structureBoundingBox, EnumFacing facing) {
        return StructureBoundingBox.getComponentToAddBoundingBox(position.getX(), position.getY(), position.getZ(), structureBoundingBox.minX, structureBoundingBox.minY, structureBoundingBox.minZ, structureBoundingBox.maxX, structureBoundingBox.maxY, structureBoundingBox.maxZ, facing);
    }

    /**
     * Fast {@link net.minecraft.world.gen.structure.StructureBoundingBox#getComponentToAddBoundingBox(int, int, int, int, int, int, int, int, int, EnumFacing)} <i>(Supports {@link net.minecraft.util.Rotation})</i>.
     * @author CoolCLK
     */
    @SuppressWarnings("unused")
    public static StructureBoundingBox getAddedBoundingBox(Vec3i position, StructureBoundingBox structureBoundingBox, Rotation rotation) {
        return getAddedBoundingBox(position, structureBoundingBox, RotationFacingHelper.fromRotation(rotation));
    }
    
    /**
     * Fast {@link net.minecraft.world.gen.structure.StructureBoundingBox#getComponentToAddBoundingBox(int, int, int, int, int, int, int, int, int, EnumFacing)}.
     * @author CoolCLK
     */
    public static StructureBoundingBox getComponentToAddBoundingBox(Vec3i position, StructureComponent component, EnumFacing facing) {
        return getAddedBoundingBox(position, component.getBoundingBox(), facing);
    }

    /**
     * Fast {@link net.minecraft.world.gen.structure.StructureBoundingBox#getComponentToAddBoundingBox(int, int, int, int, int, int, int, int, int, EnumFacing)} <i>(Supports {@link net.minecraft.util.Rotation})</i>.
     * @author CoolCLK
     */
    @SuppressWarnings("unused")
    public static StructureBoundingBox getComponentToAddBoundingBox(Vec3i position, StructureComponent component, Rotation rotation) {
        return getComponentToAddBoundingBox(position, component, RotationFacingHelper.fromRotation(rotation));
    }
}
