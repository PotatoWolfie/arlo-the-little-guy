package potatowolfie.arlo_the_little_guy.advancement;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import potatowolfie.arlo_the_little_guy.block.custom.TapeType;

public class NoOneWillBelieveYouHandler {

    private static final TapeType[] REQUIRED_TAPES = {
            TapeType.TAPE_1,
            TapeType.TAPE_2,
            TapeType.TAPE_3,
            TapeType.TAPE_4,
            TapeType.TAPE_5,
            TapeType.TAPE_6,
            TapeType.TAPE_7
    };

    public static void onTapeInserted(ServerPlayer player, TapeType tapeType) {
        MinecraftServer server = player.level().getServer();
        if (server == null) return;

        Identifier advId = Identifier.fromNamespaceAndPath(
                "arlo-the-little-guy",
                "arlrooms/no_one_will_believe_you"
        );

        AdvancementHolder advancement = server.getAdvancements().get(advId);

        if (advancement != null) {
            AdvancementProgress progress =
                    player.getAdvancements().getOrStartProgress(advancement);

            String criterionName = getCriterionName(tapeType);

            if (criterionName != null && !progress.isDone()) {
                player.getAdvancements().award(advancement, criterionName);
            }
        }
    }

    private static String getCriterionName(TapeType tapeType) {
        for (TapeType required : REQUIRED_TAPES) {
            if (required == tapeType) {
                return tapeType.getSerializedName();
            }
        }

        return null;
    }
}