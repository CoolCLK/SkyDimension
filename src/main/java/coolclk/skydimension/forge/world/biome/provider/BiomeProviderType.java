package coolclk.skydimension.forge.world.biome.provider;

public class BiomeProviderType {
    public static final net.minecraft.world.biome.provider.BiomeProviderType<SkyBiomeProviderSettings, SkyBiomeProvider> SKY = new net.minecraft.world.biome.provider.BiomeProviderType<>(SkyBiomeProvider::new, SkyBiomeProviderSettings::new);
}
