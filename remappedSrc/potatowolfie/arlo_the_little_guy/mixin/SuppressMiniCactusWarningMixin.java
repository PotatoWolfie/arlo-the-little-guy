package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.slf4j.Logger;

@Mixin(WorldChunk.class)
public class SuppressMiniCactusWarningMixin {

    @Redirect(
            method = "loadBlockEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/slf4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V",
                    remap = false
            )
    )
    private void suppressMiniCactusWarning(Logger logger, String message, Object arg1, Object arg2) {
        BlockState state = (BlockState) arg1;
        if (!state.getBlock().getTranslationKey().contains("mini_cactus")) {
            logger.warn(message, arg1, arg2);
        }
    }
}