package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class LetMeOutHandler {

    public static void grantLetMeOutAdvancement(ServerPlayer player) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        Identifier advId = Identifier.fromNamespaceAndPath("arlo-the-little-guy", "arlrooms/let_me_out");
        AdvancementHolder advancement = server.getAdvancements().get(advId);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            if (!progress.isDone()) {
                player.getAdvancements().award(advancement, "let_me_out");
            }
        }
    }
}