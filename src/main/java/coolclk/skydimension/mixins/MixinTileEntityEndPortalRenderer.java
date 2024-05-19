package coolclk.skydimension.mixins;

import coolclk.skydimension.SkyDimension;
import net.minecraft.client.renderer.tileentity.TileEntityEndPortalRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = TileEntityEndPortalRenderer.class, priority = 1001)
public abstract class MixinTileEntityEndPortalRenderer {
    @Redirect(method = "render*", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/tileentity/TileEntityEndPortalRenderer;END_PORTAL_TEXTURE:Lnet/minecraft/util/ResourceLocation;"))
    private ResourceLocation injectEndPortalTexture() {
        return new ResourceLocation(SkyDimension.MOD_ID, "textures/entity/sky_portal.png");
    }
}
