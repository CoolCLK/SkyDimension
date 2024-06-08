package coolclk.skydimension.forge.world.gen.structure;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.world.storage.loot.LootTableList;
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
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Random;

public class StructureFloatingShipPieces implements IObject {
    public static void registerPieces() {
        MapGenStructureIO.registerStructureComponent(FloatingShip.class, "FSFS");
    }

    public static class FloatingShip extends StructureComponentTemplate {
        private Rotation rotation;

        @SuppressWarnings("unused")
        public FloatingShip() {
        }

        public FloatingShip(World worldIn, BlockPos position, Rotation rotation) {
            this.world = worldIn;
            this.rotation = rotation;
            this.setup(StructureComponentTemplate.loadTemplateFromJar(worldIn.getSaveHandler().getStructureTemplateManager(), new ResourceLocation(SkyDimension.MOD_ID, "floating_ship")), position, new PlacementSettings().setIgnoreEntities(true).setRotation(rotation));
        }

        @Override
        protected void writeStructureToNBT(@Nonnull NBTTagCompound tagCompound) {
            super.writeStructureToNBT(tagCompound);
            if (this.rotation != null) {
                tagCompound.setString("Rotation", this.rotation.name());
            }
        }

        @Override
        @SuppressWarnings("CallToPrintStackTrace")
        protected void readStructureFromNBT(@Nonnull NBTTagCompound tagCompound, @Nonnull TemplateManager manager) {
            super.readStructureFromNBT(tagCompound, manager);
            if (tagCompound.hasKey("Rotation")) {
                try {
                    this.rotation = Rotation.valueOf(tagCompound.getString("Rotation"));
                } catch (IllegalArgumentException e) {
                    LOGGER.warn("Unknown rotation enum value {}.", tagCompound.getString("Rotation"));
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void handleDataMarker(@Nonnull String function, @Nonnull BlockPos position, @Nonnull World worldIn, @Nonnull Random random, @Nonnull StructureBoundingBox boundingBox) {
            switch (function) {
                case "Chest": {
                    BlockPos blockPosition = position.down();
                    if (boundingBox.isVecInside(blockPosition)) {
                        TileEntity tileentity = worldIn.getTileEntity(blockPosition);
                        if (tileentity instanceof TileEntityChest) {
                            ((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_FLOATING_BOAT, random.nextLong());
                        }
                    }
                    break;
                }
                case "Elytra": {
                    if (boundingBox.isVecInside(position)) {
                        EntityItemFrame entityItemframe = new EntityItemFrame(worldIn, position, this.rotation.rotate(EnumFacing.SOUTH));
                        entityItemframe.setDisplayedItem(new ItemStack(Items.ELYTRA, 1));
                        worldIn.spawnEntity(entityItemframe);
                    }
                    break;
                }
            }
        }
    }
}
