package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Arrays;
import java.util.List;

public class Items {
    private final static Item.ToolMaterial TOOL_MATERIAL_SKY;
    private final static ItemArmor.ArmorMaterial ARMOR_MATERIAL_SKY;
    private final static List<Item> CACHE;

    static {
        TOOL_MATERIAL_SKY = EnumHelper.addToolMaterial("sky", 3, 750, 7, 3, 14);
        ARMOR_MATERIAL_SKY = EnumHelper.addArmorMaterial("sky", "sky", 24, new int[] { 2, 5, 7, 2 } , 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
        CACHE = Arrays.asList(
                (new Item())
                        .setCreativeTab(CreativeTabs.MISC)
                        .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal")
                        .setRegistryName(SkyDimension.MOD_ID, "ice_coal"),
                (new ItemAxe(TOOL_MATERIAL_SKY) { })
                        .setCreativeTab(CreativeTabs.TOOLS)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_axe")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_axe"),
                (new ItemArmor(ARMOR_MATERIAL_SKY, 0, EntityEquipmentSlot.FEET))
                        .setCreativeTab(CreativeTabs.COMBAT)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_boots")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_boots"),
                (new ItemArmor(ARMOR_MATERIAL_SKY, 0, EntityEquipmentSlot.CHEST))
                        .setCreativeTab(CreativeTabs.COMBAT)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_chestplate")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_chestplate"),
                (new ItemArmor(ARMOR_MATERIAL_SKY, 0, EntityEquipmentSlot.HEAD))
                        .setCreativeTab(CreativeTabs.COMBAT)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_helmet")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_helmet"),
                (new ItemHoe(TOOL_MATERIAL_SKY))
                        .setCreativeTab(CreativeTabs.TOOLS)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_hoe")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_hoe"),
                (new Item())
                        .setCreativeTab(CreativeTabs.MISC)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_ingot")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_ingot"),
                (new Item())
                        .setCreativeTab(CreativeTabs.MISC)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_nugget")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_nugget"),
                (new ItemPickaxe(TOOL_MATERIAL_SKY) {  })
                        .setCreativeTab(CreativeTabs.TOOLS)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_pickaxe")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_pickaxe"),
            /*
            (new ItemTool(TOOL_MATERIAL_SKY, new HashSet<>()) {
                public int getHarvestLevel(@Nonnull ItemStack p_getHarvestLevel_1_, @Nonnull String p_getHarvestLevel_2_, @Nullable EntityPlayer p_getHarvestLevel_3_, @Nullable IBlockState p_getHarvestLevel_4_) {
                    int level = super.getHarvestLevel(p_getHarvestLevel_1_, p_getHarvestLevel_2_, p_getHarvestLevel_3_, p_getHarvestLevel_4_);
                    return level == -1 && p_getHarvestLevel_2_.equals("shovel") ? this.toolMaterial.getHarvestLevel() : level;
                }

                @Nonnull
                public Set<String> getToolClasses(@Nonnull ItemStack p_getToolClasses_1_) {
                    return ImmutableSet.of("shovel");
                }
            })
                    .setCreativeTab(CreativeTabs.TOOLS)
                    .setTranslationKey(SkyDimension.MOD_ID + ".sky_shovel")
                    .setRegistryName(SkyDimension.MOD_ID, "sky_shovel"),
            */
                (new ItemSword(TOOL_MATERIAL_SKY))
                        .setCreativeTab(CreativeTabs.COMBAT)
                        .setTranslationKey(SkyDimension.MOD_ID + ".sky_sword")
                        .setRegistryName(SkyDimension.MOD_ID, "sky_sword"));
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        Blocks.registerBlocksItem(registry);
        CACHE.forEach(registry::register);
    }

    public static void registerModel() {
        CACHE.forEach(item -> ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory")));
    }
}
