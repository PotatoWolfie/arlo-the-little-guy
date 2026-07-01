package potatowolfie.arlo_the_little_guy.client.arlrooms;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import potatowolfie.arlo_the_little_guy.advancement.IntoMadnessHandler;
import potatowolfie.arlo_the_little_guy.attachment.ModAttachments;
import potatowolfie.arlo_the_little_guy.block.custom.MiniCactusBlock;

public class StareTimeTracker {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {

                if (isPlayerLookingAtHattedMiniCactus(player)) {
                    int ticks = player.getAttachedOrCreate(ModAttachments.STARE_TIME);
                    ticks++;
                    player.setAttached(ModAttachments.STARE_TIME, ticks);

                    IntoMadnessHandler.checkProgress(player, ticks);
                } else {
                    player.setAttached(ModAttachments.STARE_TIME, 0);
                }
            }
        });
    }

    private static boolean isPlayerLookingAtHattedMiniCactus(ServerPlayer player) {
        Vec3 playerEyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);

        Vec3 endVec = playerEyePos.add(lookVec.scale(20.0));

        BlockHitResult hitResult = player.level().clip(new ClipContext(
                playerEyePos,
                endVec,
                ClipContext.Block.OUTLINE,
                ClipContext.Fluid.NONE,
                player
        ));

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos hitPos = hitResult.getBlockPos();
            BlockState state = player.level().getBlockState(hitPos);

            if (state.getBlock() instanceof MiniCactusBlock) {
                return state.getValue(MiniCactusBlock.HAS_HAT);
            }
        }

        return false;
    }
}