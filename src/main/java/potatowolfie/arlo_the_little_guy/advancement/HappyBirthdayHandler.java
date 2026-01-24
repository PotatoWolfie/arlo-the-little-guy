package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HappyBirthdayHandler {

    public static void grantHappyBirthdayAdvancement(ServerPlayerEntity player) {
        MinecraftServer server = player.getEntityWorld().getServer();
        if (server == null) return;

        Identifier advId = Identifier.of("arlo-the-little-guy", "happy_birthday");
        AdvancementEntry advancement = server.getAdvancementLoader().get(advId);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            if (!progress.isDone()) {
                player.getAdvancementTracker().grantCriterion(advancement, "happy_birthday");
            }
        }
    }
}