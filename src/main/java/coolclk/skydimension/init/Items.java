package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class Items {
    public final static Item ICE_COAL;
    public final static Item SKY_ORE;
    public final static Item SKY_INGOT;

    static {
        ICE_COAL = (new Item())
                .setCreativeTab(CreativeTabs.MISC)
                .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal")
                .setRegistryName(SkyDimension.MOD_ID, "ice_coal");
        SKY_ORE = (new ItemBlock(Blocks.SKY_ORE))
                .setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
                .setTranslationKey(SkyDimension.MOD_ID + ".sky_ore")
                .setRegistryName(SkyDimension.MOD_ID, "sky_ore");
        SKY_INGOT = (new Item())
                .setCreativeTab(CreativeTabs.MISC)
                .setTranslationKey(SkyDimension.MOD_ID + ".sky_ingot")
                .setRegistryName(SkyDimension.MOD_ID, "sky_ingot");
    }
}
