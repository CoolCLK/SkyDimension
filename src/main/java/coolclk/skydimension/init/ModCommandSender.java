package coolclk.skydimension.init;

import coolclk.skydimension.SkyDimension;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ModCommandSender implements ICommandSender {
    private final MinecraftServer server;

    public ModCommandSender(MinecraftServer server) {
        this.server = server;
    }

    @Nonnull
    @Override
    public String getName() {
        return SkyDimension.MOD_ID;
    }

    @Override
    public boolean canUseCommand(int i, @Nonnull String s) {
        return true;
    }

    @Nonnull
    @Override
    public World getEntityWorld() {
        return getServer().worlds[0];
    }

    @Nonnull
    @Override
    public MinecraftServer getServer() {
        return server;
    }
}
