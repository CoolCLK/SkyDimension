package coolclk.skydimension.world;

import coolclk.skydimension.world.chunk.ChunkProviderSky;
import coolclk.skydimension.world.dimension.DimensionSky;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldSky extends World {
    public WorldSky() {
        super(  Minecraft.getMinecraft().world.getSaveHandler(),
                Minecraft.getMinecraft().world.getWorldInfo(),
                DimensionSky.getWorldProvider(),
                Minecraft.getMinecraft().mcProfiler,
                Minecraft.getMinecraft().world.isRemote);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return new ChunkProviderSky(this, this.getSeed());
    }

    @Override
    protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
        return false;
    }
}
