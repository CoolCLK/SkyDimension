package coolclk.skydimension.forge.block;

import coolclk.skydimension.IObject;
import net.minecraft.block.properties.PropertyBool;

/**
 * Block properties will put here.
 * @author CoolCLK
 */
public class BlockProperties implements IObject {
    public static final PropertyBool IS_SKY;

    static {
        IS_SKY = PropertyBool.create("sky");
    }
}
