package coolclk.skydimension.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class ChunkPrimer extends net.minecraft.world.chunk.ChunkPrimer {
    private final char[] data = new char[65536];
    public void setBlockState(int index, IBlockState state) {
        this.data[index] = (char) Block.BLOCK_STATE_IDS.get(state);
    }
}
