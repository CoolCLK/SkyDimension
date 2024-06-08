package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.IObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Better {@link net.minecraft.world.gen.structure.StructureComponentTemplate}
 * @author CoolCLK
 */
public abstract class StructureComponentTemplate extends net.minecraft.world.gen.structure.StructureComponentTemplate implements IObject {
    protected World world;
    protected boolean generated;

    public static Template loadTemplateFromJar(TemplateManager templateManager, ResourceLocation location) {
        return templateManager.getTemplate(null, location);
    }

    @Override
    protected void setup(@Nonnull Template templateIn, @Nonnull BlockPos position, @Nonnull PlacementSettings settings) {
        super.setup(templateIn, position, settings);
    }

    protected void setComponentType(int type) {
        this.componentType = type;
    }

    @Override
    public boolean addComponentParts(@Nonnull World worldIn, @Nonnull Random randomIn, @Nonnull StructureBoundingBox structureBoundingBoxIn) {
        if (this.generated) {
            return false;
        }
        this.generated = true;
        return !super.addComponentParts(worldIn, randomIn, structureBoundingBoxIn);
    }

    @Override
    protected void writeStructureToNBT(@Nonnull NBTTagCompound tagCompound) {
        super.writeStructureToNBT(tagCompound);
        tagCompound.setBoolean("Generated", this.generated);
    }

    @Override
    protected void readStructureFromNBT(@Nonnull NBTTagCompound tagCompound, @Nonnull TemplateManager manager) {
        super.readStructureFromNBT(tagCompound, manager);
        this.generated = tagCompound.hasKey("Generated") && tagCompound.getBoolean("Generated");
    }
}
