package coolclk.skydimension.forge.block;

import net.minecraft.block.properties.PropertyBool;

/**
 * Block properties will put here.
 * @author CoolCLK7065
 */
public class BlockProperties {
    public static final PropertyBool IS_SKY;

    static {
        IS_SKY = PropertyBool.create("sky");
    }
}
