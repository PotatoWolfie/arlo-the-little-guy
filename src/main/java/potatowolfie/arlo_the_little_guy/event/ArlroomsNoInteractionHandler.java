package potatowolfie.arlo_the_little_guy.event;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import potatowolfie.arlo_the_little_guy.advancement.LetMeOutHandler;

public class ArlroomsNoInteractionHandler {

    public static void register() {

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                String dimensionId = world.dimension().identifier().toString();

                if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {
                    GameType gameMode = serverPlayer.gameMode.getGameModeForPlayer();
                    if (gameMode == GameType.SURVIVAL || gameMode == GameType.ADVENTURE) {
                        LetMeOutHandler.grantLetMeOutAdvancement(serverPlayer);
                        return InteractionResult.FAIL;
                    }
                }
            }
            return InteractionResult.PASS;
        });

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, blockEntity) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                String dimensionId = world.dimension().identifier().toString();
                if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {
                    GameType gameMode = serverPlayer.gameMode.getGameModeForPlayer();
                    if (gameMode == GameType.SURVIVAL || gameMode == GameType.ADVENTURE) {
                        return false;
                    }
                }
            }
            return true;
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);

            if (player instanceof ServerPlayer serverPlayer) {
                String dimensionId = world.dimension().identifier().toString();

                if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {
                    GameType gameMode = serverPlayer.gameMode.getGameModeForPlayer();
                    if (gameMode == GameType.SURVIVAL || gameMode == GameType.ADVENTURE) {

                        if (stack.getItem() instanceof BlockItem) {
                            return InteractionResult.FAIL;
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        });
    }
}