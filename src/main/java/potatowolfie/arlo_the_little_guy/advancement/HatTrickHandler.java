package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public class HatTrickHandler {

    private static final String[] REQUIRED_HATS = {
            "arlo-the-little-guy:bowler_hat",
            "arlo-the-little-guy:tricorn",
            "arlo-the-little-guy:straw_hat",
            "arlo-the-little-guy:cowboy_hat",
            "arlo-the-little-guy:top_hat",
            "arlo-the-little-guy:sun_hat",
            "arlo-the-little-guy:crown",
            "arlo-the-little-guy:stop_sign",
            "arlo-the-little-guy:prismarine_pipis",
            "minecraft:bucket",
            "minecraft:cactus_flower"
    };

    public static void onHatPlaced(ServerPlayer player, String hatType) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        Identifier advId = Identifier.fromNamespaceAndPath("arlo-the-little-guy", "hat_trick");
        AdvancementHolder advancement = server.getAdvancements().get(advId);

        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
            String criterionName = getCriterionNameFromHatType(hatType);

            if (criterionName != null && !progress.isDone()) {
                player.getAdvancements().award(advancement, criterionName);
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