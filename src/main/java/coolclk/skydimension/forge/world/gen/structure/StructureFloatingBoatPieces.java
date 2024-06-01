package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.SkyDimension;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nonnull;
import java.util.Random;

public class StructureFloatingBoatPieces {
    public static void registerPieces() {
        MapGenStructureIO.registerStructureComponent(FloatingBoat.class, "SDFB");
    }

    public static class FloatingBoat extends StructureComponentTemplate {
        private Rotation rotation;

        @SuppressWarnings("unused")
        public FloatingBoat() {
        }

        public FloatingBoat(TemplateManager templateManager, BlockPos position, Rotation rotation) {
            super(0);
            this.templatePosition = position;
            this.rotation = rotation;
            this.loadTemplate(templateManager);
        }

        private void loadTemplate(TemplateManager manager) {
            Template template = manager.getTemplate(null, new ResourceLocation(SkyDimension.MOD_ID, "floating_ship"));
            PlacementSettings placementsettings = new PlacementSettings().setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementsettings);
        }

        @Override
        protected void writeStructureToNBT(@Nonnull NBTTagCompound tagCompound) {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Rotation", this.rotation.name());
        }

        @Override
        protected void readStructureFromNBT(@Nonnull NBTTagCompound tagCompound, @Nonnull TemplateManager manager) {
            super.readStructureFromNBT(tagCompound, manager);
            this.rotation = Rotation.valueOf(tagCompound.getString("Rotation"));
            this.loadTemplate(manager);
        }

        @Override
        protected void handleDataMarker(@Nonnull String function, @Nonnull BlockPos pos, @Nonnull World worldIn, @Nonnull Random rand, @Nonnull StructureBoundingBox sbb) {
            if (function.startsWith("Chest")) {
                BlockPos blockpos = pos.down();

                if (sbb.isVecInside(blockpos))
                {
                    TileEntity tileentity = worldIn.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntityChest)
                    {
                        ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, rand.nextLong());
                    }
                }
            } else if (function.startsWith("Elytra")) {
                EntityItemFrame entityitemframe = new EntityItemFrame(worldIn, pos, this.rotation.rotate(EnumFacing.SOUTH));
                entityitemframe.setDisplayedItem(new ItemStack(Items.ELYTRA));
                worldIn.spawnEntity(entityitemframe);
            }
        }
    }
}
