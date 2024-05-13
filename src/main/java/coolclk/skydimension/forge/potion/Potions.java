package coolclk.skydimension.forge.potion;

import coolclk.skydimension.SkyDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

/**
 * Simple teleporter with only function which teleport player.
 * @author CoolCLK
 */
public class Potions {
    public final static Potion SLOW_FALLING;

    static {
        SLOW_FALLING = new Potion(false, 0xF3CFB9) {
            @Override
            public void performEffect(@Nonnull EntityLivingBase entityLivingBase, int amplifier) {
                if (!entityLivingBase.onGround && !(entityLivingBase.isInLava() || entityLivingBase.isInWater()) && entityLivingBase.motionY < 0) {
                    double fallSpeed = 0.1D / 2;
                    entityLivingBase.motionY += fallSpeed;
                    entityLivingBase.fallDistance = 0;
                }
                entityLivingBase.setNoGravity(false);
            }

            @Override
            public boolean isReady(int duration, int amplifier) {
                return true;
            }

            @Override
            public boolean hasStatusIcon() {
                return true;
            }

            public void renderInventoryEffect(@Nonnull PotionEffect potionEffect, @Nonnull Gui gui, int x, int y, float alpha) {
                this.renderEffect(x + 8, y + 8);
            }

            public void renderHUDEffect(@Nonnull PotionEffect potionEffect, @Nonnull Gui gui, int x, int y, float rotation, float alpha) {
                this.renderEffect(x + 4, y + 4);
            }

            public void renderEffect(int x, int y) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(SkyDimension.MOD_ID, "textures/mob_effect/slow_falling.png"));
                Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
            }
        }.setPotionName("potion.skydimension.slow_falling.name").setRegistryName(SkyDimension.MOD_ID, "slow_falling");
    }

    public static void registerPotions(IForgeRegistry<Potion> registry) {
        registry.register(SLOW_FALLING);
    }
}
