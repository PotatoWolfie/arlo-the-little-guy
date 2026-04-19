package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelChunk.class)
public class SuppressMiniCactusWarningMixin {

    @Redirect(
            method = "promotePendingBlockEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private void suppressMiniCactusWarning(Logger logger, String message, Object arg1, Object arg2) {
        BlockState state = (BlockState) arg1;
        if (!state.getBlock().getDescriptionId().contains("mini_cactus")) {
            logger.warn(message, arg1, arg2);
        }
    }
}