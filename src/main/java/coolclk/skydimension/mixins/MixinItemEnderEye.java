package coolclk.skydimension.mixins;

import com.google.common.base.Predicates;
import coolclk.skydimension.forge.block.BlockProperties;
import coolclk.skydimension.forge.entity.item.EntitySkyEye;
import coolclk.skydimension.forge.item.ItemSkyEye;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

import static net.minecraft.block.BlockEndPortalFrame.EYE;
import static net.minecraft.block.BlockEndPortalFrame.FACING;

@Mixin(value = ItemEnderEye.class, priority = 1001)
public abstract class MixinItemEnderEye {
    @Unique
    private BlockPattern skyDimension_1_12_2$portalShape;

    /**
     * Add property {@link coolclk.skydimension.forge.block.BlockProperties#IS_SKY} and make it <code>true</code>.
     * @author CoolCLK7065
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;withProperty(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;", ordinal = 0))
    private <T extends Comparable<T>, V extends T> IBlockState injectPlaceBlock(IBlockState instance, IProperty<T> tiProperty, V v) {
        return ((Object) this) instanceof ItemSkyEye ? instance.withProperty(EYE, true).withProperty(BlockProperties.IS_SKY, true) : instance;
    }

    /**
     * Add new pattern.
     * @author CoolCLK7065
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockEndPortalFrame;getOrCreatePortalShape()Lnet/minecraft/block/state/pattern/BlockPattern;"))
    private BlockPattern injectPattern() {
        boolean isSky = ((Object) this) instanceof ItemSkyEye;
        if (skyDimension_1_12_2$portalShape == null) {
            skyDimension_1_12_2$portalShape = FactoryBlockPattern.start()
                    .aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
                    .where('?', BlockWorldState.hasState(BlockStateMatcher.ANY))
                    .where('^',
                            BlockWorldState.hasState(
                                    BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME)
                                            .where(EYE, Predicates.equalTo(true))
                                            .where(FACING, Predicates.equalTo(EnumFacing.SOUTH))
                                            .where(BlockProperties.IS_SKY, Predicates.equalTo(isSky))
                            )
                    )
                    .where('>',
                            BlockWorldState.hasState(
                                    BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME)
                                            .where(EYE, Predicates.equalTo(true))
                                            .where(FACING, Predicates.equalTo(EnumFacing.WEST))
                                            .where(BlockProperties.IS_SKY, Predicates.equalTo(isSky))
                            )
                    )
                    .where('v',
                            BlockWorldState.hasState(
                                    BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME)
                                            .where(EYE, Predicates.equalTo(true))
                                            .where(FACING, Predicates.equalTo(EnumFacing.NORTH))
                                            .where(BlockProperties.IS_SKY, Predicates.equalTo(isSky))
                            )
                    )
                    .where('<',
                            BlockWorldState.hasState(
                                    BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME)
                                            .where(EYE, Predicates.equalTo(true))
                                            .where(FACING, Predicates.equalTo(EnumFacing.EAST))
                                            .where(BlockProperties.IS_SKY, Predicates.equalTo(isSky))
                            )

                    )
                    .build();
        }

        return skyDimension_1_12_2$portalShape;
    }

    /**
     * Add new pattern.
     * @author CoolCLK7065
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z", ordinal = 1))
    private boolean injectPortal(World instance, BlockPos pos, IBlockState blockState, int flags) {
        if (((Object) this) instanceof ItemSkyEye) {
            return instance.setBlockState(pos, coolclk.skydimension.forge.init.Blocks.SKY_PORTAL.getDefaultState(), flags);
        }
        return true;
    }

    @Redirect(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", ordinal = 0))
    private boolean injectRightClick(World worldIn, Entity entity) {
        if (((Object) this) instanceof ItemSkyEye) {
            EntityEnderEye instance = (EntityEnderEye) entity;
            EntitySkyEye entitySkyEye = new EntitySkyEye(instance.getEntityWorld(), instance.getPositionVector().x, instance.getPositionVector().y, instance.getPositionVector().z);
            entitySkyEye.moveTowards(Objects.requireNonNull(((WorldServer) worldIn).getChunkProvider().getNearestStructurePos(worldIn, "Stronghold", new BlockPos(instance.getPosition()), false)));
            worldIn.spawnEntity(entitySkyEye);
        }
        return true;
    }
}
