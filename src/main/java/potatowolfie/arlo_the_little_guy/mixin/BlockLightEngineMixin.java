package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.BlockLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import potatowolfie.arlo_the_little_guy.client.light.FlashlightDynamicLight;

@Mixin(BlockLightEngine.class)
public class BlockLightEngineMixin {

    @Inject(method = "getEmission", at = @At("HEAD"), cancellable = true)
    private void injectFlashlightLight(long packedPos, BlockState state, CallbackInfoReturnable<Integer> cir) {
        BlockPos flashPos = FlashlightDynamicLight.flashlightLightPos;
        if (flashPos == null) return;

        BlockPos currentEnginePos = BlockPos.of(packedPos);
        if (currentEnginePos.equals(flashPos)) {
            cir.setReturnValue(5);
        }
    }
}