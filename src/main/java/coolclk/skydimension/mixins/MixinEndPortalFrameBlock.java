package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.state.properties.BlockStateProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.state.IProperty;
import net.minecraft.state.IStateHolder;
import net.minecraft.state.StateContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EndPortalFrameBlock.class, priority = 1001)
public abstract class MixinEndPortalFrameBlock {
    @SuppressWarnings("unchecked")
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/state/StateContainer;getBaseState()Lnet/minecraft/state/IStateHolder;"))
    private <S extends IStateHolder<S>> S injectInit(StateContainer<Block, BlockState> instance) {
        return (S) instance.getBaseState().with(BlockStateProperties.END_FRAME_SKY, false);
    }

    /**
     * Make the property {@link BlockStateProperties#END_FRAME_SKY} is allowed in the block container.
     * @author CoolCLK
     */
    @ModifyArg(method = "fillStateContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/state/StateContainer$Builder;add([Lnet/minecraft/state/IProperty;)Lnet/minecraft/state/StateContainer$Builder;"))
    private IProperty<?>[] injectFillStateContainer(IProperty<?>[] properties) {
        IProperty<?>[] iProperties = new IProperty<?>[properties.length + 1];
        System.arraycopy(properties, 0, iProperties, 0, properties.length);
        iProperties[iProperties.length - 1] = BlockStateProperties.END_FRAME_SKY;
        return iProperties;
    }
}
