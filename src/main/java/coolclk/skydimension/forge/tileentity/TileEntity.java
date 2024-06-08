package coolclk.skydimension.forge.tileentity;

import coolclk.skydimension.IObject;

import static net.minecraft.tileentity.TileEntity.register;

/**
 * Handle tile entities and the render of them.<br>
 * <i><strong>Note: </strong>Here registering domain is <code>minecraft</code> and it is not changeable or you use Mixin.</i>
 * @author CoolCLK
 */
public class TileEntity implements IObject {
    static {
        register("sky_portal", TileEntitySkyPortal.class);
    }
}
