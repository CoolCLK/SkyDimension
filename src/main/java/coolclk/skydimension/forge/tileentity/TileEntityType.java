package coolclk.skydimension.forge.tileentity;

import com.mojang.datafixers.DataFixUtils;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;

/**
 * Handle tile entities and the render of them.<br>
 * <i><strong>Note: </strong>Here registering domain is <code>minecraft</code> and it is not changeable or you use Mixin.</i>
 * @author CoolCLK
 */
public class TileEntityType {
    public static final net.minecraft.tileentity.TileEntityType<SkyPortalTileEntity> SKY_PORTAL;

    static {
        SKY_PORTAL = register(new ResourceLocation(SkyDimension.MOD_ID, "sky_portal"), net.minecraft.tileentity.TileEntityType.Builder.create(SkyPortalTileEntity::new, Blocks.SKY_PORTAL));
    }

    @SuppressWarnings({"deprecation", "SameParameterValue"})
    private static <T extends TileEntity> net.minecraft.tileentity.TileEntityType<T> register(ResourceLocation location, net.minecraft.tileentity.TileEntityType.Builder<T> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, location, builder.build(DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, location.getPath())));
    }
}
