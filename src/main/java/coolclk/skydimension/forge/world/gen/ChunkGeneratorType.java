package coolclk.skydimension.forge.world.gen;

public class ChunkGeneratorType {
    public static final net.minecraft.world.gen.ChunkGeneratorType<SkyGenerationSettings, SkyChunkGenerator> SKY = new net.minecraft.world.gen.ChunkGeneratorType<>(SkyChunkGenerator::new, false, SkyGenerationSettings::new);
}
