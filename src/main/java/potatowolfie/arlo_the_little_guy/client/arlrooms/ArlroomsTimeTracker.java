package potatowolfie.arlo_the_little_guy.client.arlrooms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.level.ServerPlayer;
import potatowolfie.arlo_the_little_guy.advancement.ArlroomsTimeHandler;
import potatowolfie.arlo_the_little_guy.attachment.ModAttachments;

public class ArlroomsTimeTracker {

    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            if (server.getTickCount() % 20 != 0) {
                return;
            }

            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                String dimensionId =
                        player.level().dimension().identifier().toString();

                if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {

                    int ticks = player.getAttachedOrCreate(
                            ModAttachments.ARLROOMS_TIME
                    );

                    ticks += 20;

                    player.setAttached(
                            ModAttachments.ARLROOMS_TIME,
                            ticks
                    );

                    ArlroomsTimeHandler.checkProgress(player, ticks);

                } else {

                    player.setAttached(
                            ModAttachments.ARLROOMS_TIME,
                            0
                    );
                }
            }
        });
    }
}