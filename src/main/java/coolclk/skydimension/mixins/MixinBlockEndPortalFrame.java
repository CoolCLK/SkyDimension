package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.block.BlockProperties;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockEndPortalFrame.class, priority = 1001)
public abstract class MixinBlockEndPortalFrame {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockStateContainer;getBaseState()Lnet/minecraft/block/state/IBlockState;"))
    private IBlockState injectInit(BlockStateContainer instance) {
        return instance.getBaseState().withProperty(BlockProperties.IS_SKY, false);
    }

    /**
     * Make the property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} is allowed in the block container.
     * @author CoolCLK7065
     */
    @ModifyArg(method = "createBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockStateContainer;<init>(Lnet/minecraft/block/Block;[Lnet/minecraft/block/properties/IProperty;)V"), index = 1)
    private IProperty<?>[] injectCreateBlockState(IProperty<?>[] properties) {
        IProperty<?>[] iProperties = new IProperty<?>[properties.length + 1];
        System.arraycopy(properties, 0, iProperties, 0, properties.length);
        iProperties[iProperties.length - 1] = BlockProperties.IS_SKY;
        return iProperties;
    }
}
