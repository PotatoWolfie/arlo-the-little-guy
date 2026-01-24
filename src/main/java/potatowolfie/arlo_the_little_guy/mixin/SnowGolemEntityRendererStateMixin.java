package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.SnowGolemEntityRenderer;
import net.minecraft.client.render.entity.state.SnowGolemEntityRenderState;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.top_hat.SnowGolemRenderStateAccessor;

@Mixin(SnowGolemEntityRenderer.class)
public class SnowGolemEntityRendererStateMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/SnowGolemEntity;Lnet/minecraft/client/render/entity/state/SnowGolemEntityRenderState;F)V",
            at = @At("TAIL"))
    private void captureArmorState(SnowGolemEntity snowGolem, SnowGolemEntityRenderState state, float tickDelta, CallbackInfo ci) {
        ((SnowGolemRenderStateAccessor)state).arloTheLittleGuy$setBodyArmor(snowGolem.getBodyArmor());
    }
}