package coolclk.skydimension.forge.event;

import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.block.BlockProperties;
import coolclk.skydimension.forge.block.PropertyHelper;
import coolclk.skydimension.forge.init.Blocks;
import coolclk.skydimension.forge.init.Items;
import coolclk.skydimension.forge.world.teleporter.SpawnTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.village.VillageSiegeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Handling events in Forge.
 * @author CoolCLK
 */
@EventBusSubscriber(modid = SkyDimension.MOD_ID)
public class EventHandler {
    public static void onServerStarting(FMLServerStartingEvent event) {
        registryCommand(event);
    }

    private static void registryCommand(@Nullable FMLServerStartingEvent serverEvent) {
        List<ICommand> commands = Collections.singletonList(new CommandBase() {
            @Nonnull
            @Override
            public String getName() {
                return "tp-sky";
            }

            @Nonnull
            @Override
            public String getUsage(@Nonnull ICommandSender sender) {
                return new TextComponentTranslation("command.skydimension.tp-sky.usage").getFormattedText();
            }

            @Override
            public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) {
                EntityPlayer player = null;
                if (args.length > 0) {
                    player = server.getPlayerList().getPlayerByUsername(args[0]);
                } else if (sender instanceof EntityPlayer) {
                    player = (EntityPlayer) sender;
                }
                if (player != null) {
                    if (player.dimension != coolclk.skydimension.forge.world.DimensionType.SKY.getId()) {
                        player.changeDimension(coolclk.skydimension.forge.world.DimensionType.SKY.getId(), new SpawnTeleporter());
                    }
                }
            }
        });
        commands.forEach(command -> {
            if (serverEvent != null) {
                serverEvent.registerServerCommand(command);
                return;
            }
            ClientCommandHandler.instance.registerCommand(command);
        });
    }

    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(Items.SKY_EYE.setRegistryName(new ResourceLocation(SkyDimension.MOD_ID, "sky_eye")));
    }

    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(Blocks.SKY_PORTAL.setRegistryName(new ResourceLocation(SkyDimension.MOD_ID, "sky_portal"))); // Test
    }

    @SubscribeEvent
    public static void onRegisterItemModel(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Items.SKY_EYE, 0, new ModelResourceLocation(Objects.requireNonNull(Items.SKY_EYE.getRegistryName()), "inventory"));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EntityPlayer player = event.player;
        if (player.dimension == coolclk.skydimension.forge.world.DimensionType.SKY.getId()) { // TODO Remove it
            if (player.getPosition().getY() <= 0) {
                player.changeDimension(DimensionType.OVERWORLD.getId(), (world, entity, yaw) -> {
                    BlockPos pos = world.getTopSolidOrLiquidBlock(entity.getPosition());
                    entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        IBlockState blockState = event.getWorld().getBlockState(event.getPos());
        if (blockState.getBlock() == net.minecraft.init.Blocks.END_PORTAL_FRAME) {
            if (blockState.getValue(BlockEndPortalFrame.EYE)) {
                blockState = blockState.withProperty(BlockEndPortalFrame.EYE, false);
                Item spawnItem = net.minecraft.init.Items.ENDER_EYE;
                if (PropertyHelper.getBlockPropertyValue(event.getWorld(), event.getPos(), BlockProperties.IS_SKY, false)) {
                    blockState = blockState.withProperty(BlockProperties.IS_SKY, false);
                    spawnItem = Items.SKY_EYE;
                }
                event.getEntityPlayer().swingArm(event.getHand());
                if (!event.getWorld().isRemote && !event.getEntityPlayer().isCreative() && !event.getEntityPlayer().isSpectator()) {
                    event.getWorld().spawnEntity(new EntityItem(event.getWorld(), event.getPos().getX(), event.getPos().getY() + 0.5D, event.getPos().getZ(), new ItemStack(spawnItem)));
                }
                event.setCanceled(true);
            }
        }
        event.getWorld().setBlockState(event.getPos(), blockState);
    }

    @SubscribeEvent
    public static void onVillageSiege(VillageSiegeEvent event) {
        if (event.getWorld().provider.getDimensionType() == coolclk.skydimension.forge.world.DimensionType.SKY) {
            event.setCanceled(true);
        }
    }
}
