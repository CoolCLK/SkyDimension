package coolclk.skydimension.forge.injection;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Load Mixin if tweak class can not be loaded successfully.
 * @author CoolCLK7065
 */
public class MixinFMLLoadingPlugin implements IFMLLoadingPlugin {
    /**
     * Init event.
     * @author CoolCLK7065
     */
    public MixinFMLLoadingPlugin() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.skydimension.json");
    }

    /**
     * Empty here.
     * @author CoolCLK7065
     */
    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    /**
     * Empty here.
     * @author CoolCLK7065
     */
    @Override
    public String getModContainerClass() {
        return null;
    }

    /**
     * Empty here.
     * @author CoolCLK7065
     */
    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    /**
     * Empty here.
     * @author CoolCLK7065
     */
    @Override
    public void injectData(Map<String, Object> data) {

    }

    /**
     * Empty here.
     * @author CoolCLK7065
     */
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
