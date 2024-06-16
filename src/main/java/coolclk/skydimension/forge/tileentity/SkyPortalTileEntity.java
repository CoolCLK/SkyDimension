package coolclk.skydimension.forge.tileentity;

import net.minecraft.tileentity.EndPortalTileEntity;

/**
 * Init the tile entity. Others are handling by Mixin.
 * @author CoolCLK
 */
public class SkyPortalTileEntity extends EndPortalTileEntity {
    public SkyPortalTileEntity(net.minecraft.tileentity.TileEntityType<?> type) {
        super(type);
    }

    public SkyPortalTileEntity() {
        this(TileEntityType.SKY_PORTAL);
    }
}
