package coolclk.skydimension.forge.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Help to operate the properties of blocks in a world.
 * @author CoolCLK
 */
public class PropertyHelper {
    /**
     * Get a block property value, or if it not exists, will add the property to the block with default value.
     * @param world The world that block at
     * @param pos To locate the block
     * @param property Target property
     * @param defaultValue Return and set the value to block if the block did not exist the property
     * @author CoolCLK
     */
    public static <T extends Comparable<T>> T getBlockPropertyValue(World world, BlockPos pos, IProperty<T> property, T defaultValue) {
        IBlockState state = world.getBlockState(pos);
        T value = getStatePropertyValue(state, property, null);
        if (value != null) {
            return value;
        } else {
            state = state.withProperty(property, defaultValue);
        }
        world.setBlockState(pos, state);
        return defaultValue;
    }

    /**
     * Get a block state property value, or if it not exists, will return the default value.
     * @param property Target property
     * @param defaultValue Return and set the value to block if the block did not exist the property
     * @author CoolCLK
     */
    public static <T extends Comparable<T>> T getStatePropertyValue(IBlockState state, IProperty<T> property, T defaultValue) {
        if (state.getPropertyKeys().contains(property)) {
            return state.getValue(property);
        }
        return defaultValue;
    }
}
