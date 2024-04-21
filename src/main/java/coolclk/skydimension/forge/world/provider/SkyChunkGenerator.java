package coolclk.skydimension.forge.world.provider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SkyChunkGenerator extends ChunkGenerator {
    public static final Codec<SkyChunkGenerator> CODEC = RecordCodecBuilder.create((builder) -> builder.group(BiomeProvider.CODEC.fieldOf("biome_source").forGetter((p_236096_0_) -> p_236096_0_.biomeSource), Codec.LONG.fieldOf("seed").stable().forGetter((map) -> map.seed), DimensionSettings.OVERWORLD));
    private Long seed;
    private Supplier<DimensionSettings> settings;

    public SkyChunkGenerator(BiomeProvider biomeProvider, Long seed, Supplier<DimensionSettings> dimensionSettingsSupplier) {
        super(biomeProvider, biomeProvider, dimensionSettingsSupplier.get().structureSettings(), seed);
    }

    @Override
    @Nonnull
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    @Nonnull
    public ChunkGenerator withSeed(long seed) {
        return new SkyChunkGenerator(this.biomeSource.withSeed(seed), seed, this.settings);
    }

    @Override
    public void buildSurfaceAndBedrock(@Nonnull WorldGenRegion region, @Nonnull IChunk chunk) {

    }

    @Override
    public void fillFromNoise(@Nonnull IWorld world, @Nonnull StructureManager manager, @Nonnull IChunk chunk) {

    }

    @Override
    public int getBaseHeight(int x, int y, @Nonnull Heightmap.Type type) {
        return 0;
    }

    @Override
    @Nonnull
    public IBlockReader getBaseColumn(int x, int y) {
        return null;
    }

    static {
        Registry.register(Registry.CHUNK_GENERATOR, "sky", SkyChunkGenerator.CODEC);
    }
}
