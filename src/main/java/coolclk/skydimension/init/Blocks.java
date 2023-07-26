package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.Random;

public class Blocks {
    public final static Block ICE_COAL_BLOCK;
    public final static Block ICE_COAL_ORE;
    public final static Block SKY_ORE;

    static {
        ICE_COAL_BLOCK = (new Block(Material.ROCK))
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal_block")
                .setRegistryName(SkyDimension.MOD_ID, "ice_coal_block");
        ICE_COAL_ORE = (new BlockOre() {
            @Nonnull
            @Override
            public Item getItemDropped(IBlockState state, Random rand, int fortune) {
                return Items.ICE_COAL;
            }
        })
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setTranslationKey(SkyDimension.MOD_ID + ".ice_coal_ore")
                .setRegistryName(SkyDimension.MOD_ID, "ice_coal_ore");
        SKY_ORE = (new BlockOre())
                .setHardness(3.0F)
                .setResistance(5.0F)
                .setTranslationKey(SkyDimension.MOD_ID + ".sky_ore")
                .setRegistryName(SkyDimension.MOD_ID, "sky_ore");
    }
}