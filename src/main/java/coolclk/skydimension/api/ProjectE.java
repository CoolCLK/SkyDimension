package coolclk.skydimension.api;

import coolclk.skydimension.init.ModCommandSender;
import net.minecraft.server.MinecraftServer;

public class ProjectE {
    public final static String MOD_ID = "projecte";

    public static void registerEMC(MinecraftServer server) {
        server.commandManager.executeCommand(new ModCommandSender(server), "/projecte setEMC skydimension:ice_coal 128");
        server.commandManager.executeCommand(new ModCommandSender(server), "/projecte setEMC skydimension:sky_ingot 384");
    }
}
