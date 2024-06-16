package coolclk.skydimension.forge.client.renderer.tileentity;

import coolclk.skydimension.forge.tileentity.TileEntitySkyPortal;

import static net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.instance;

/**
 * Handle tile entities and the render of them.<br>
 * <i><strong>Note: </strong>Here registering domain is <code>minecraft</code> and it is not changeable or you use Mixin.</i>
 * @author CoolCLK
 */
public class TileEntityRendererHandler {
    static {
        instance.renderers.put(TileEntitySkyPortal.class, new TileEntitySkyPortalRenderer() {
            {
                this.rendererDispatcher = instance;
            }
        });
    }
}
