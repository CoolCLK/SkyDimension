package coolclk.skydimension.init;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ToolMaterial {
    public final static Item.ToolMaterial SKY;

    static {
        SKY = EnumHelper.addToolMaterial("tool_material_sky", 3, 750, 7, 3, 14);
    }
}
