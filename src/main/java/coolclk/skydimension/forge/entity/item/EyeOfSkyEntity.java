package coolclk.skydimension.forge.entity.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.EyeOfEnderEntity;
import net.minecraft.world.World;

/**
 * Init the tile entity. Others are handling by Mixin.
 * @author CoolCLK
 */
public class EyeOfSkyEntity extends EyeOfEnderEntity {
    /**
     * Create a new throwing sky eye.
     * @author CoolCLK
     */
    @SuppressWarnings("unused")
    public EyeOfSkyEntity(EntityType<? extends EyeOfEnderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    /**
     * Create a new throwing sky eye.
     * @author CoolCLK
     */
    public EyeOfSkyEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void registerData() {
        super.registerData();
    }
}
