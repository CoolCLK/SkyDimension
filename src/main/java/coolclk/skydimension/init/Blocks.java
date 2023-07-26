package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Blocks {
    private final static List<Block> CACHE = Arrays.asList(
            (new Block(Material.ROCK))
                    .setHardness(3.0F)
                    .setResistance(5.0F)
                    .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal_block")
                    .setRegistryName(SkyDimension.MOD_ID, "ice_coal_block"),
            (new BlockOre() {
                @Override
                public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
                    return Item.getByNameOrId("ice_coal");
                }
            })
                    .setHardness(3.0F)
                    .setResistance(5.0F)
                    .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal_ore")
                    .setRegistryName(SkyDimension.MOD_ID, "ice_coal_ore"),
            (new BlockOre())
                    .setHardness(3.0F)
                    .setResistance(5.0F)
                    .setTranslationKey(SkyDimension.MOD_ID + ".sky_ore")
                    .setRegistryName(SkyDimension.MOD_ID, "sky_ore"),
            (new Block(Material.ROCK))
                    .setHardness(3.0F)
                    .setResistance(5.0F)
                    .setTranslationKey(SkyDimension.MOD_ID + ".sky_block")
                    .setRegistryName(SkyDimension.MOD_ID, "sky_block"));
    private final static List<Item> ITEM_CACHE = new ArrayList<>();

    static {
        CACHE.forEach(block -> {
            Item item = new ItemBlock(block)
                    .setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
                    .setTranslationKey(block.getTranslationKey())
                    .setRegistryName(block.getRegistryName());
            ITEM_CACHE.add(item);
        });
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        CACHE.forEach(registry::register);
    }

    public static void registerBlocksItem(IForgeRegistry<Item> registry) {
        ITEM_CACHE.forEach(registry::register);
    }

    public static void registerModel() {
        ITEM_CACHE.forEach(item -> {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        });
    }
}