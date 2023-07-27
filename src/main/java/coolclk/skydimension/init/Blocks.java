package coolclk.skydimension.init;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.registries.IForgeRegistry;

public class Blocks {
    public final static Block ICE_COAL_BLOCK = RegistryTool.createBlock("ice_coal_block");
    public final static Block ICE_COAL_ORE = RegistryTool.createOre("ice_coal_ore", Items.ICE_COAL);
    public final static Block SKY_ORE = RegistryTool.createOre("sky_ore");
    public final static Block SKY_BLOCK = RegistryTool.createBlock("sky_block");
    private final static Block[] CACHE;
    private final static Item[] ITEM_CACHE;

    static {
        CACHE = new Block[] { ICE_COAL_BLOCK, ICE_COAL_ORE, SKY_ORE, SKY_BLOCK };
        ITEM_CACHE = new Item[CACHE.length];
        for (int i = 0; i < CACHE.length; i++) {
            Block block = CACHE[i];
            ITEM_CACHE[i] = new ItemBlock(block)
                    .setRegistryName(block.getRegistryName());
        }
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(CACHE);
    }

    public static void registerBlocksItem(IForgeRegistry<Item> registry) {
        registry.registerAll(ITEM_CACHE);
    }

    public static void registerModel() {
        for (Item item : ITEM_CACHE) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }
}