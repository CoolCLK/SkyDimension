package coolclk.skydimension.init;

import com.google.common.collect.ImmutableSet;
import coolclk.skydimension.SkyDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RegistryTool {
    public static Item createItem(String name) {
        return (new Item())
                .setCreativeTab(CreativeTabs.MISC)
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createArmor(String name, ItemArmor.ArmorMaterial material, EntityEquipmentSlot slot) {
        return (new ItemArmor(material, 0, slot) {
            @Override
            public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
                if (this.getArmorMaterial() == coolclk.skydimension.init.ArmorMaterial.SKY) {
                    if (player.inventory.armorItemInSlot(slot.getSlotIndex()).getItem() == this) {
                        player.addPotionEffect(new PotionEffect(Potions.SLOW_FALLING, 1, 1));
                    }
                }
                super.onArmorTick(world, player, itemStack);
            }
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createAxe(String name, Item.ToolMaterial material, float attackDamage, float attackSpeed) {
        return (new ItemAxe(material, attackDamage, attackSpeed) {
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createHoe(String name, Item.ToolMaterial material) {
        return (new ItemHoe(material) {
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createPickaxe(String name, Item.ToolMaterial material) {
        return (new ItemPickaxe(material) {
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createShovel(String name, Item.ToolMaterial material) {
        return (new ItemTool(material, new HashSet<>()) {
            public int getHarvestLevel(@Nonnull ItemStack p_getHarvestLevel_1_, @Nonnull String p_getHarvestLevel_2_, @Nullable EntityPlayer p_getHarvestLevel_3_, @Nullable IBlockState p_getHarvestLevel_4_) {
                int level = super.getHarvestLevel(p_getHarvestLevel_1_, p_getHarvestLevel_2_, p_getHarvestLevel_3_, p_getHarvestLevel_4_);
                return level == -1 && p_getHarvestLevel_2_.equals("shovel") ? this.toolMaterial.getHarvestLevel() : level;
            }

            @Nonnull
            public Set<String> getToolClasses(@Nonnull ItemStack p_getToolClasses_1_) {
                return ImmutableSet.of("shovel");
            }
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Item createSword(String name, Item.ToolMaterial material) {
        return (new ItemSword(material) {
            public void onUsingTick(@Nonnull ItemStack itemStack, @Nonnull EntityLivingBase entityLivingBase, int slot) {
                entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1, 2));
            }
        })
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Block createBlock(String name) {
        return (new Block(Material.ROCK))
                .setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Block createOre(String name, @Nullable Item droppedItem) {
        return (new BlockOre() {
            @Nonnull
            @Override
            public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
                return droppedItem != null ? droppedItem : Item.getItemFromBlock(this);
            }
        })
                .setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setTranslationKey(SkyDimension.MOD_ID + "." + name)
                .setRegistryName(SkyDimension.MOD_ID, name);
    }

    public static Block createOre(String name) {
        return createOre(name, null);
    }
}
