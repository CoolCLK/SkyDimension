package coolclk.skydimension.forge.world;

/**
 * Forge mod dimensions.
 * @author CoolCLK
 */
public class ModDimension {
    public static final net.minecraftforge.common.ModDimension SKY = net.minecraftforge.common.ModDimension.withFactory(coolclk.skydimension.forge.world.dimension.SkyDimension::new);
}
