package coolclk.skydimension.mixins;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import coolclk.skydimension.forge.client.renderer.tileentity.TileEntitySkyPortalRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TileEntityEndPortalRenderer.class, priority = 1001)
public abstract class MixinTileEntityEndPortalRenderer implements IObject {
    @Shadow @Final private static ResourceLocation END_SKY_TEXTURE;
    @Shadow @Final private static ResourceLocation END_PORTAL_TEXTURE;

    @Redirect(method = "render*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/tileentity/TileEntityEndPortalRenderer;END_SKY_TEXTURE:Lnet/minecraft/util/ResourceLocation;"))
    @SuppressWarnings("UnreachableCode")
    private ResourceLocation injectEndSkyTexture() {
        return ((Object) this) instanceof TileEntitySkyPortalRenderer ? new ResourceLocation(SkyDimension.MOD_ID, "textures/environment/sky_sky.png") : END_SKY_TEXTURE;
    }

    @Redirect(method = "render*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/tileentity/TileEntityEndPortalRenderer;END_PORTAL_TEXTURE:Lnet/minecraft/util/ResourceLocation;"))
    @SuppressWarnings("UnreachableCode")
    private ResourceLocation injectEndPortalTexture() {
        return ((Object) this) instanceof TileEntitySkyPortalRenderer ? new ResourceLocation(SkyDimension.MOD_ID, "textures/entity/sky_portal.png") : END_PORTAL_TEXTURE;
    }
}
