package potatowolfie.arlo_the_little_guy.structure.arlrooms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public final class ArlroomsRuntime {

    private ArlroomsRuntime() {}

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(ArlroomsRuntime::tickServer);
    }

    private static void tickServer(MinecraftServer server) {
        ArlroomsExpansionManager.tick();
        ArlroomsExpansionManager.tickResends(server);
    }
}