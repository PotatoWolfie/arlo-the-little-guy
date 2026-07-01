package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class ArlroomsTimeHandler {

    public static final int SEVEN_DAYS = 24000 * 7;
    public static final int FOURTEEN_DAYS = 24000 * 14;

    public static void checkProgress(ServerPlayer player, int arlroomsTicks) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        if (arlroomsTicks >= SEVEN_DAYS) {
            grantAdvancement(
                    server,
                    player,
                    Identifier.fromNamespaceAndPath(
                            "arlo-the-little-guy",
                            "arlrooms/day_after_day_after_day"
                    ),
                    "day_after_day_after_day"
            );
        }

        if (arlroomsTicks >= FOURTEEN_DAYS) {
            grantAdvancement(
                    server,
                    player,
                    Identifier.fromNamespaceAndPath(
                            "arlo-the-little-guy",
                            "arlrooms/do_you_like_need_a_map"
                    ),
                    "do_you_like_need_a_map"
            );
        }
    }

    private static void grantAdvancement(
            MinecraftServer server,
            ServerPlayer player,
            Identifier advancementId,
            String criterion
    ) {
        AdvancementHolder advancement =
                server.getAdvancements().get(advancementId);

        if (advancement != null) {
            AdvancementProgress progress =
                    player.getAdvancements().getOrStartProgress(advancement);

            if (!progress.isDone()) {
                player.getAdvancements().award(
                        advancement,
                        criterion
                );
            }
        }
    }
}