package coolclk.skydimension.forge.item;

import coolclk.skydimension.IObject;
import net.minecraft.item.EnderEyeItem;

/**
 * Init the item. Others are handling by Mixin.
 * @author CoolCLK
 */
public class SkyEyeItem extends EnderEyeItem implements IObject {
    public SkyEyeItem(Properties properties) {
        super(properties);
    }
}
