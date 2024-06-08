package coolclk.skydimension.forge.init;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.item.ItemSkyEye;

/**
 * Init items.
 * @author CoolCLK
 */
public class Items implements IObject {
    public static final ItemSkyEye SKY_EYE = (ItemSkyEye) new ItemSkyEye().setUnlocalizedName(SkyDimension.MOD_ID + ".eyeOfSky");
}
