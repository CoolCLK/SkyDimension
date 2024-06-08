package coolclk.skydimension.forge.entity.item;

import coolclk.skydimension.IObject;
import coolclk.skydimension.forge.init.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.world.World;

/**
 * Init the tile entity. Others are handling by Mixin.
 * @author CoolCLK
 */
public class EntitySkyEye extends EntityEnderEye implements IObject {
    /**
     * Create a new throwing sky eye.
     * @author CoolCLK
     */
    @SuppressWarnings("unused")
    public EntitySkyEye(World worldIn) {
        super(worldIn);
    }

    /**
     * Create a new throwing sky eye.
     * @author CoolCLK
     */
    public EntitySkyEye(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    static {
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityEnderEye.class, new RenderSnowball<>(Minecraft.getMinecraft().getRenderManager(), Items.SKY_EYE, Minecraft.getMinecraft().getRenderItem()));
    }
}
