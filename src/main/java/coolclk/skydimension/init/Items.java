package coolclk.skydimension.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class Items {
    public final static Item ICE_COAL = RegistryTool.createItem("ice_coal");
    public final static Item SKY_AXE = RegistryTool.createAxe("sky_axe", ToolMaterial.SKY, 7, -3);
    public final static Item SKY_BOOTS = RegistryTool.createArmor("sky_boots", ArmorMaterial.SKY, EntityEquipmentSlot.FEET);
    public final static Item SKY_CHESTPLATE = RegistryTool.createArmor("sky_chestplate", ArmorMaterial.SKY, EntityEquipmentSlot.CHEST);
    public final static Item SKY_HELMET = RegistryTool.createArmor("sky_helmet", ArmorMaterial.SKY, EntityEquipmentSlot.HEAD);
    public final static Item SKY_HOE = RegistryTool.createHoe("sky_hoe", ToolMaterial.SKY);
    public final static Item SKY_INGOT = RegistryTool.createItem("sky_ingot");
    public final static Item SKY_LEGGINGS = RegistryTool.createArmor("sky_leggings", ArmorMaterial.SKY, EntityEquipmentSlot.LEGS);
    public final static Item SKY_NUGGET = RegistryTool.createItem("sky_nugget");
    public final static Item SKY_PICKAXE = RegistryTool.createPickaxe("sky_pickaxe", ToolMaterial.SKY);
    public final static Item SKY_SHOVEL = RegistryTool.createShovel("sky_shovel", ToolMaterial.SKY);
    public final static Item SKY_SWORD = RegistryTool.createSword("sky_sword", ToolMaterial.SKY);
    private final static Item[] CACHE;

    static {
        CACHE = new Item[] { ICE_COAL, SKY_AXE, SKY_BOOTS, SKY_CHESTPLATE, SKY_CHESTPLATE, SKY_HELMET, SKY_HOE, SKY_INGOT, SKY_LEGGINGS, SKY_NUGGET, SKY_PICKAXE, SKY_SHOVEL, SKY_SWORD };
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        Blocks.registerBlocksItem(registry);
        registry.registerAll(CACHE);
    }

    public static void registerModel() {
        for (Item item : CACHE) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}
