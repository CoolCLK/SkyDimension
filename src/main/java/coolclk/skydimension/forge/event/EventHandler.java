package coolclk.skydimension.forge.event;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.state.properties.BlockStateProperties;
import coolclk.skydimension.forge.block.PropertyHelper;
import coolclk.skydimension.forge.block.Blocks;
import coolclk.skydimension.forge.item.Items;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

/**
 * Handling events in Forge.
 * @author CoolCLK
 */
@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class EventHandler implements IObject {
    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Items.SKY_EYE.setRegistryName(new ResourceLocation(SkyDimension.MOD_ID, "sky_eye")));
    }

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(Blocks.SKY_PORTAL.setRegistryName(new ResourceLocation(SkyDimension.MOD_ID, "sky_portal"))); // Test
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        BlockState blockState = event.getWorld().getBlockState(event.getPos());
        if (blockState.getBlock() == net.minecraft.block.Blocks.END_PORTAL_FRAME) {
            if (blockState.get(EndPortalFrameBlock.EYE)) {
                blockState = blockState.with(EndPortalFrameBlock.EYE, false);
                Item spawnItem = net.minecraft.item.Items.ENDER_EYE;
                if (PropertyHelper.getBlockPropertyValue(event.getWorld(), event.getPos(), BlockStateProperties.END_FRAME_SKY, false)) {
                    blockState = blockState.with(BlockStateProperties.END_FRAME_SKY, false);
                    spawnItem = Items.SKY_EYE;
                }
                event.getWorld().playSound(null, event.getPos(), SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!event.getWorld().isRemote && !event.getEntityPlayer().isCreative() && !event.getEntityPlayer().isSpectator()) {
                    event.getWorld().addEntity(new ItemEntity(event.getWorld(), event.getPos().getX(), event.getPos().getY() + 1, event.getPos().getZ(), new ItemStack(spawnItem)));
                }
                event.setCanceled(true);
            }
        }
        event.getWorld().setBlockState(event.getPos(), blockState);
    }

    @SubscribeEvent
    public static void onVillageSiege(VillageSiegeEvent event) {
        if (!event.getWorld().isRemote && event.getWorld().dimension.getType() == coolclk.skydimension.forge.world.DimensionType.SKY) {
            event.setCanceled(true);
        }
    }
}
