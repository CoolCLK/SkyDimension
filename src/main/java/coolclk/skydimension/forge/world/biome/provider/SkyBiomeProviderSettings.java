package coolclk.skydimension.forge.world.biome.provider;

import coolclk.skydimension.forge.world.gen.SkyGenerationSettings;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class SkyBiomeProviderSettings implements IBiomeProviderSettings {
    private WorldInfo worldInfo;
    private SkyGenerationSettings generatorSettings;

    public SkyBiomeProviderSettings() {
    }

    @SuppressWarnings("UnusedReturnValue")
    public SkyBiomeProviderSettings setWorldInfo(WorldInfo worldInfo) {
        this.worldInfo = worldInfo;
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public SkyBiomeProviderSettings setGeneratorSettings(SkyGenerationSettings settings) {
        this.generatorSettings = settings;
        return this;
    }

    public WorldInfo getWorldInfo() {
        return this.worldInfo;
    }

    public SkyGenerationSettings getGeneratorSettings() {
        return this.generatorSettings;
    }
}
