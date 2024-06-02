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
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static coolclk.skydimension.SkyDimension.MOD_LOGGER;

public class StructureFloatingShipPieces {
    public static void registerPieces() {
        MapGenStructureIO.registerStructureComponent(FloatingShip.class, "FSFS");
    }

    public static class FloatingShip extends StructureComponentTemplate {
        private Rotation rotation;
        private World world;
        private boolean generated;
        private boolean templateLoaded;
        private TemplateManager templateManager;

        @SuppressWarnings("unused")
        public FloatingShip() {
        }

        public FloatingShip(TemplateManager templateManager, World worldIn, BlockPos position, Rotation rotation) {
            super(0);
            this.templatePosition = position;
            this.world = worldIn;
            this.rotation = rotation;
            this.generated = false;
            this.templateLoaded = false;
            this.templateManager = templateManager;
        }

        private void loadTemplate() {
            if (this.templateManager == null) {
                MOD_LOGGER.warn("The structure component ({}, {}, {}) cannot use temple because of loading.", this.templatePosition.getX(), this.templatePosition.getY(), this.templatePosition.getZ());
                return;
            }
            Template template = this.templateManager.getTemplate(null, new ResourceLocation(SkyDimension.MOD_ID, "floating_ship"));
            PlacementSettings placementsettings = new PlacementSettings().setIgnoreEntities(true).setRotation(this.rotation);
            this.setup(template, this.templatePosition, placementsettings);
            this.templateLoaded = true;
        }

        protected void setComponentType(int type) {
            this.componentType = type;
        }

        protected void setBoundingBox(StructureBoundingBox boundingBox) {
            this.boundingBox = boundingBox;
        }

        @Override
        public void buildComponent(@Nullable StructureComponent startComponent, @Nullable List<StructureComponent> components, @Nonnull Random random) {
            if (!this.templateLoaded) {
                MOD_LOGGER.warn("The structure component ({}, {}, {}) may be unloaded fully.", this.templatePosition.getX(), this.templatePosition.getY(), this.templatePosition.getZ());
                this.loadTemplate();
            }
            if (!this.generated) {
                if (startComponent != null && components != null) {
                    super.buildComponent(startComponent, components, random);
                    components.add(this);
                }
                this.template.addBlocksToWorld(this.world, this.templatePosition, this.placeSettings);
                this.generated = true;
            }
            this.addComponentParts(this.world, random, this.getBoundingBox());
        }

        @Override
        protected void writeStructureToNBT(@Nonnull NBTTagCompound tagCompound) {
            super.writeStructureToNBT(tagCompound);
            tagCompound.setString("Rotation", this.rotation.name());
            tagCompound.setBoolean("Generated", this.generated);
        }

        @Override
        protected void readStructureFromNBT(@Nonnull NBTTagCompound tagCompound, @Nonnull TemplateManager manager) {
            super.readStructureFromNBT(tagCompound, manager);
            this.rotation = Rotation.valueOf(tagCompound.getString("Rotation"));
            this.generated = tagCompound.hasKey("Generated") && tagCompound.getBoolean("Generated");
            this.loadTemplate();
        }

        @Override
        protected void handleDataMarker(@Nonnull String function, @Nonnull BlockPos pos, @Nonnull World worldIn, @Nonnull Random rand, @Nonnull StructureBoundingBox sbb) {
            if (function.startsWith("Chest")) {
                BlockPos blockPosition = pos.down();

                if (sbb.isVecInside(blockPosition)) {
                    TileEntity tileentity = worldIn.getTileEntity(blockPosition);
                    if (tileentity instanceof TileEntityChest) {
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
