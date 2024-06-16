package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.entity.item.EyeOfSkyEntity;
import coolclk.skydimension.forge.item.Items;
import net.minecraft.entity.item.EyeOfEnderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EyeOfEnderEntity.class)
public class MixinEyeOfEnderEntity {
    @Shadow @Final private static DataParameter<ItemStack> field_213864_b;

    @SuppressWarnings({"UnreachableCode", "unchecked"})
    @Redirect(method = "registerData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/datasync/EntityDataManager;register(Lnet/minecraft/network/datasync/DataParameter;Ljava/lang/Object;)V"))
    private <T> void injectData(EntityDataManager instance, DataParameter<T> parameter, T value) {
        if (parameter == field_213864_b) {
            instance.register(parameter, ((Object) this) instanceof EyeOfSkyEntity ? (T) new ItemStack(Items.SKY_EYE, 1) : value);
        }
    }
}
