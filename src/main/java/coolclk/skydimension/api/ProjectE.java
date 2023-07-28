package coolclk.skydimension.api;

import net.minecraft.server.MinecraftServer;

public class ProjectE {
    public static void registerEMC(MinecraftServer server) {
        server.commandManager.executeCommand(server, "projecte setEMC skydimension:ice_coal 128");
        server.commandManager.executeCommand(server, "projecte setEMC skydimension:sky_ingot 384");
    }
}
