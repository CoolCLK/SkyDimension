package coolclk.skydimension.forge.world.gen;

import coolclk.skydimension.IObject;
import coolclk.skydimension.SkyDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.*;

import java.util.function.Supplier;

public class ChunkGeneratorType implements IObject {
    public static final net.minecraft.world.gen.ChunkGeneratorType<SkyGenerationSettings, SkyChunkGenerator> SKY = register(new ResourceLocation(SkyDimension.MOD_ID, "sky"), SkyChunkGenerator::new, SkyGenerationSettings::new, false);

    @SuppressWarnings({"deprecation", "SameParameterValue"})
    private static <C extends GenerationSettings, T extends ChunkGenerator<C>> net.minecraft.world.gen.ChunkGeneratorType<C, T> register(ResourceLocation location, IChunkGeneratorFactory<C, T> factory, Supplier<C> settings, boolean isOptionForBuffetWorld) {
        return Registry.register(Registry.CHUNK_GENERATOR_TYPE, location, new net.minecraft.world.gen.ChunkGeneratorType<>(factory, isOptionForBuffetWorld, settings));
    }
}
