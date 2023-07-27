package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

public class Potions {
    public final static Potion SLOW_FALLING;
    private final static Potion[] CACHE;

    static {
        SLOW_FALLING = new Potion(false, 0xF3CFB9) {
            @Override
            public boolean hasStatusIcon() {
                return false;
            }

            public void renderInventoryEffect(@Nonnull PotionEffect potionEffect, @Nonnull Gui gui, int x, int y, float alpha) {
                this.renderHUDEffect(potionEffect, gui, x, y, 0, alpha);
            }

            public void renderHUDEffect(@Nonnull PotionEffect potionEffect, @Nonnull Gui gui, int x, int y, float p_renderHUDEffect_5_, float alpha) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(SkyDimension.MOD_ID, "textures/icons/potions/slow_falling.png"));
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
            }
        }.setPotionName("potion.skydimension.slow_falling.name").setRegistryName(SkyDimension.MOD_ID, "slow_falling");
        CACHE = new Potion[] { SLOW_FALLING };
    }

    public static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.registerAll(CACHE);
    }
}
