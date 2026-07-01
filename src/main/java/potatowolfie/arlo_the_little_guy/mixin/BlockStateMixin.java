package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateMixin {

    @Inject(method = "getDestroyProgress", at = @At("HEAD"), cancellable = true)
    private void forceBedrockHardness(Player player, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (player != null && !player.isCreative() && !player.isSpectator()) {
            String dimensionId = player.level().dimension().identifier().toString();

            if (dimensionId.equals("arlo-the-little-guy:arlrooms")) {
                cir.setReturnValue(0.0F);
            }
        }
    }
}