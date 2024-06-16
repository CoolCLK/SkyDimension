package coolclk.skydimension.forge.tileentity;

import coolclk.skydimension.forge.block.Blocks;

/**
 * Handle tile entities and the render of them.
 * @author CoolCLK
 */
public class TileEntityType {
    public static final net.minecraft.tileentity.TileEntityType<SkyPortalTileEntity> SKY_PORTAL;

    static {
        //noinspection DataFlowIssue
        SKY_PORTAL = net.minecraft.tileentity.TileEntityType.Builder.create(SkyPortalTileEntity::new, Blocks.SKY_PORTAL).build(null);
    }
}
