package coolclk.skydimension.mixins;

import com.google.common.base.Predicates;
import coolclk.skydimension.forge.block.Blocks;
import coolclk.skydimension.forge.state.properties.BlockStateProperties;
import coolclk.skydimension.forge.entity.item.EyeOfSkyEntity;
import coolclk.skydimension.forge.item.SkyEyeItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EyeOfEnderEntity;
import net.minecraft.item.EnderEyeItem;
import net.minecraft.state.IProperty;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

import static net.minecraft.block.EndPortalFrameBlock.EYE;
import static net.minecraft.block.EndPortalFrameBlock.FACING;

@Mixin(value = EnderEyeItem.class, priority = 1001)
public abstract class MixinItemEnderEye {

    /**
     * Add property {@link BlockStateProperties#END_FRAME_SKY} and make it <code>true</code>.
     * @author CoolCLK
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;with(Lnet/minecraft/state/IProperty;Ljava/lang/Comparable;)Ljava/lang/Object;", ordinal = 0))
    @SuppressWarnings({"UnreachableCode", "unchecked"})
    private <T extends Comparable<T>, V extends T, S> S injectPlaceBlock(BlockState instance, IProperty<T> iProperty, V v) {
        instance = instance.with(iProperty, v);
        return (S) (((Object) this) instanceof SkyEyeItem ? instance.with(BlockStateProperties.END_FRAME_SKY, true) : instance);
    }

    /**
     * Add new pattern.
     * @author CoolCLK
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/EndPortalFrameBlock;getOrCreatePortalShape()Lnet/minecraft/block/pattern/BlockPattern;"))
    private BlockPattern injectPattern() {
        return skyDimension_1_12_2$createPortalShape(((Object) this) instanceof SkyEyeItem);
    }

    @Unique
    private BlockPattern skyDimension_1_12_2$createPortalShape(boolean isSky) {
        return BlockPatternBuilder.start()
                .aisle("?vvv?", ">???<", ">???<", ">???<", "?^^^?")
                .where('?', CachedBlockInfo.hasState(BlockStateMatcher.ANY))
                .where('^',
                        CachedBlockInfo.hasState(
                                BlockStateMatcher.forBlock(net.minecraft.block.Blocks.END_PORTAL_FRAME)
                                        .where(EYE, Predicates.equalTo(true))
                                        .where(FACING, Predicates.equalTo(Direction.SOUTH))
                                        .where(BlockStateProperties.END_FRAME_SKY, Predicates.equalTo(isSky))
                        )
                )
                .where('>',
                        CachedBlockInfo.hasState(
                                BlockStateMatcher.forBlock(net.minecraft.block.Blocks.END_PORTAL_FRAME)
                                        .where(EYE, Predicates.equalTo(true))
                                        .where(FACING, Predicates.equalTo(Direction.WEST))
                                        .where(BlockStateProperties.END_FRAME_SKY, Predicates.equalTo(isSky))
                        )
                )
                .where('v',
                        CachedBlockInfo.hasState(
                                BlockStateMatcher.forBlock(net.minecraft.block.Blocks.END_PORTAL_FRAME)
                                        .where(EYE, Predicates.equalTo(true))
                                        .where(FACING, Predicates.equalTo(Direction.NORTH))
                                        .where(BlockStateProperties.END_FRAME_SKY, Predicates.equalTo(isSky))
                        )
                )
                .where('<',
                        CachedBlockInfo.hasState(
                                BlockStateMatcher.forBlock(net.minecraft.block.Blocks.END_PORTAL_FRAME)
                                        .where(EYE, Predicates.equalTo(true))
                                        .where(FACING, Predicates.equalTo(Direction.EAST))
                                        .where(BlockStateProperties.END_FRAME_SKY, Predicates.equalTo(isSky))
                        )

                )
                .build();
    }

    /**
     * Add new pattern.
     * @author CoolCLK
     */
    @Redirect(method = "onItemUse", at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;END_PORTAL:Lnet/minecraft/block/Block;", ordinal = 0))
    @SuppressWarnings("UnreachableCode")
    private Block injectPortal() {
        return ((Object) this) instanceof SkyEyeItem ? Blocks.SKY_PORTAL : net.minecraft.block.Blocks.END_PORTAL;
    }

    @Redirect(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addEntity(Lnet/minecraft/entity/Entity;)Z", ordinal = 0))
    @SuppressWarnings("UnreachableCode")
    private boolean injectRightClick(final World worldIn, final Entity entity) {
        if (((Object) this) instanceof SkyEyeItem) {
            EyeOfEnderEntity instance = (EyeOfEnderEntity) entity;
            EyeOfSkyEntity entitySkyEye = new EyeOfSkyEntity(instance.getEntityWorld(), instance.getPositionVector().x, instance.getPositionVector().y, instance.getPositionVector().z);
            entitySkyEye.moveTowards(Objects.requireNonNull(worldIn.getChunkProvider().getChunkGenerator().findNearestStructure(worldIn, "Stronghold", new BlockPos(instance.getPosition()), 100, false)));
            return worldIn.addEntity(entitySkyEye);
        } else {
            return worldIn.addEntity(entity);
        }
    }
}
