package coolclk.skydimension.forge.init;

import coolclk.skydimension.forge.block.BlockSkyPortal;
import net.minecraft.block.material.Material;

/**
 * Init blocks.
 * @author CoolCLK7065
 */
public class Blocks {
    public static final BlockSkyPortal SKY_PORTAL = (BlockSkyPortal) new BlockSkyPortal(Material.PORTAL).setHardness(-1.0F).setResistance(6000000.0F);
}
