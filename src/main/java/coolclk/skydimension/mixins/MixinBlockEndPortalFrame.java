package coolclk.skydimension.mixins;

import coolclk.skydimension.forge.block.BlockProperties;
import coolclk.skydimension.forge.block.PropertyHelper;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockEndPortalFrame.class, priority = 1001)
public abstract class MixinBlockEndPortalFrame {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockStateContainer;getBaseState()Lnet/minecraft/block/state/IBlockState;"))
    private IBlockState injectInit(BlockStateContainer instance) {
        return instance.getBaseState().withProperty(BlockProperties.IS_SKY, false);
    }

    /**
     * Make the property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} is allowed in the block container.
     * @author CoolCLK
     */
    @ModifyArg(method = "createBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/BlockStateContainer;<init>(Lnet/minecraft/block/Block;[Lnet/minecraft/block/properties/IProperty;)V"), index = 1)
    private IProperty<?>[] injectCreateBlockState(IProperty<?>[] properties) {
        IProperty<?>[] iProperties = new IProperty<?>[properties.length + 1];
        System.arraycopy(properties, 0, iProperties, 0, properties.length);
        iProperties[iProperties.length - 1] = BlockProperties.IS_SKY;
        return iProperties;
    }

    /**
     * Make place block with the property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} .
     * @author CoolCLK
     */
    @Inject(method = "getStateForPlacement", at = @At(value = "RETURN"), cancellable = true)
    private void injectStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, CallbackInfoReturnable<IBlockState> cir) {
        cir.setReturnValue(cir.getReturnValue().withProperty(BlockProperties.IS_SKY, false));
    }

    /**
     * Turn meta to a block state with the property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} .
     * @author CoolCLK
     */
    @Inject(method = "getStateFromMeta", at = @At(value = "RETURN"), cancellable = true)
    private void injectStateFromMeta(int meta, CallbackInfoReturnable<IBlockState> cir) {
        cir.setReturnValue(cir.getReturnValue().withProperty(BlockProperties.IS_SKY, (meta & 8) != 0));
    }

    /**
     * Make the property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} is savable.
     * @author CoolCLK
     */
    @Inject(method = "getMetaFromState", at = @At(value = "RETURN"), cancellable = true)
    private void injectMetaFromState(IBlockState state, CallbackInfoReturnable<Integer> cir) {
        if (PropertyHelper.getStatePropertyValue(state, BlockProperties.IS_SKY, false)) {
            cir.setReturnValue(cir.getReturnValue() | 8);
        }
    }
}
