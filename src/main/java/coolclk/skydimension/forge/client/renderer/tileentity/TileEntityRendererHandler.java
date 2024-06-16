package coolclk.skydimension.forge.client.renderer.tileentity;

import coolclk.skydimension.forge.tileentity.SkyPortalTileEntity;

import static net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.instance;

/**
 * Handle tile entities and the render of them.<br>
 * <i><strong>Note: </strong>Here registering domain is <code>minecraft</code> and it is not changeable or you use Mixin.</i>
 * @author CoolCLK
 */
public class TileEntityRendererHandler {
    static {
        instance.setSpecialRenderer(SkyPortalTileEntity.class, new SkyPortalTileEntityRenderer());
    }
}
