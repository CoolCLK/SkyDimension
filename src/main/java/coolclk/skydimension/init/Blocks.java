package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

public class Blocks {
    public final static Block SKY_ORE = (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setRegistryName(SkyDimension.MOD_ID, "sky_ore");
}
