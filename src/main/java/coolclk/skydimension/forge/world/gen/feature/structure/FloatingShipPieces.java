package coolclk.skydimension.forge.world.gen.feature.structure;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.world.storage.loot.LootTables;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

public class FloatingShipPieces {
    public static class Piece extends TemplateStructurePiece {
        private final Rotation rotation;
        private final boolean overwrite;

        public Piece(TemplateManager templateManager, BlockPos position, Rotation rotation, boolean overwrite) {
            super(StructurePieceType.FLOATING_SHIP, 0);
            this.templatePosition = position;
            this.rotation = rotation;
            this.overwrite = overwrite;
            this.loadTemplate(templateManager);
        }

        @SuppressWarnings("unused")
        public Piece(TemplateManager templateManager, CompoundNBT nbt) {
            super(StructurePieceType.FLOATING_SHIP, nbt);
            this.rotation = Rotation.valueOf(nbt.getString("Rotation"));
            this.overwrite = nbt.getBoolean("Overwrite");
            this.loadTemplate(templateManager);
        }

        @Override
        protected void readAdditional(@Nonnull CompoundNBT nbt) {
            super.readAdditional(nbt);
            nbt.putString("Rotation", this.rotation.name());
            nbt.putBoolean("Overwrite", this.overwrite);
        }

        @SuppressWarnings("ConstantValue")
        private void loadTemplate(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(new ResourceLocation(SkyDimension.MOD_ID, "floating_ship"));
            PlacementSettings settings = new PlacementSettings().setIgnoreEntities(true);
            if (this.overwrite) {
                settings = settings.copy().addProcessor(this.overwrite ? BlockIgnoreStructureProcessor.STRUCTURE_BLOCK : BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
            }
            this.setup(template, this.templatePosition, settings);
        }

        @Override
        protected void handleDataMarker(@Nonnull String metadata, @Nonnull BlockPos position, @Nonnull IWorld worldIn, @Nonnull Random random, @Nonnull MutableBoundingBox boundingBox) {
            if (metadata.startsWith("Chest")) {
                BlockPos chestPosition = position.down();
                if (boundingBox.isVecInside(chestPosition)) {
                    LockableLootTileEntity.setLootTable(worldIn, random, chestPosition, LootTables.CHESTS_FLOATING_BOAT);
                }
            } else if (metadata.startsWith("Elytra")) {
                ItemFrameEntity entity = new ItemFrameEntity(worldIn.getWorld(), position, this.rotation.rotate(Direction.SOUTH));
                entity.setDisplayedItemWithUpdate(new ItemStack(Items.ELYTRA), false);
                worldIn.addEntity(entity);
            }
        }
    }
}
