package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.init.Items;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityEnderEye.class, priority = 1001)
public abstract class MixinEntityEnderEye {
    /**
     * Change dropped item to {@link coolclk.skydimension.forge.init.Items#SKY_EYE} instead of {@link net.minecraft.init.Items#ENDER_EYE}.
     * @author CoolCLK7065
     */
    @Redirect(method = "onUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/init/Items;ENDER_EYE:Lnet/minecraft/item/Item;"))
    private Item injectDropItem() {
        return ((Object) this) instanceof EntityEnderEye ? Items.SKY_EYE : net.minecraft.init.Items.ENDER_EYE;
    }
}
