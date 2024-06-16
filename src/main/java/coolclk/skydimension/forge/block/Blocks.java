package coolclk.skydimension.forge.block;

import coolclk.skydimension.IObject;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

/**
 * Init blocks.
 * @author CoolCLK
 */
public class Blocks implements IObject {
    public static final SkyPortalBlock SKY_PORTAL = new SkyPortalBlock(Block.Properties.create(Material.PORTAL, MaterialColor.PINK).doesNotBlockMovement().lightValue(15).hardnessAndResistance(-1.0F, 3600000.0F).noDrops());
}
