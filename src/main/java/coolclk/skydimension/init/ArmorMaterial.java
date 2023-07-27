package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

public class ArmorMaterial {
    public final static ItemArmor.ArmorMaterial SKY;

    static {
        SKY = EnumHelper.addArmorMaterial("armor_material_sky", SkyDimension.MOD_ID + ":sky", 24, new int[]{2, 5, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1);
    }
}
