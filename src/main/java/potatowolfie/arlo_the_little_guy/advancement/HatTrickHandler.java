package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class HatTrickHandler {

    private static final String[] REQUIRED_HATS = {
            "arlo-the-little-guy:bowler_hat",
            "arlo-the-little-guy:tricorn",
            "arlo-the-little-guy:straw_hat",
            "arlo-the-little-guy:cowboy_hat",
            "arlo-the-little-guy:top_hat",
            "arlo-the-little-guy:sun_hat",
            "arlo-the-little-guy:crown",
            "minecraft:bucket",
            "minecraft:cactus_flower"
    };

    public static void onHatPlaced(ServerPlayerEntity player, String hatType) {
        MinecraftServer server = player.getEntityWorld().getServer();
        if (server == null) return;

        Identifier advId = Identifier.of("arlo-the-little-guy", "hat_trick");
        AdvancementEntry advancement = server.getAdvancementLoader().get(advId);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            String criterionName = getCriterionNameFromHatType(hatType);

            if (criterionName != null && !progress.isDone()) {
                player.getAdvancementTracker().grantCriterion(advancement, criterionName);
            }
        }
    }

    private static String getCriterionNameFromHatType(String hatType) {
        for (String requiredHat : REQUIRED_HATS) {
            if (requiredHat.equals(hatType)) {
                int colonIndex = hatType.indexOf(':');
                if (colonIndex != -1 && colonIndex < hatType.length() - 1) {
                    return hatType.substring(colonIndex + 1);
                }
            }
        }
        return null;
    }
}