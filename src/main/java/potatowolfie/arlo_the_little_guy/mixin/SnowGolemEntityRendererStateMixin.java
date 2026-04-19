package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.renderer.entity.SnowGolemRenderer;
import net.minecraft.client.renderer.entity.state.SnowGolemRenderState;
import net.minecraft.world.entity.animal.golem.SnowGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemRenderStateAccessor;

@Mixin(SnowGolemRenderer.class)
public class SnowGolemEntityRendererStateMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/golem/SnowGolem;Lnet/minecraft/client/renderer/entity/state/SnowGolemRenderState;F)V",
            at = @At("TAIL"))
    private void captureArmorState(SnowGolem snowGolem, SnowGolemRenderState state, float tickDelta, CallbackInfo ci) {
        ((SnowGolemRenderStateAccessor)state).arloTheLittleGuy$setBodyArmor(snowGolem.getBodyArmorItem());
    }
}