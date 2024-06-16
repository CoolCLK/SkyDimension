package coolclk.skydimension.forge.item;

import coolclk.skydimension.IObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * Init items.
 * @author CoolCLK
 */
public class Items implements IObject {
    public static final SkyEyeItem SKY_EYE = new SkyEyeItem(new Item.Properties().group(ItemGroup.MISC));
}
