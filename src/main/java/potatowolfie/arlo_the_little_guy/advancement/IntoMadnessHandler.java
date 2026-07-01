package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class IntoMadnessHandler {

    public static final int THREE_DAYS = 24000 * 3;

    public static void checkProgress(ServerPlayer player, int stareTicks) {
        if (stareTicks < THREE_DAYS) return;

        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        AdvancementHolder advancement = server.getAdvancements().get(
                Identifier.fromNamespaceAndPath(
                        "arlo-the-little-guy",
                        "arlrooms/into_madness"
                )
        );

        if (advancement != null) {
            AdvancementProgress progress =
                    player.getAdvancements().getOrStartProgress(advancement);

            if (!progress.isDone()) {
                player.getAdvancements().award(
                        advancement,
                        "into_madness"
                );
            }
        }
    }
}