package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.state.PigEntityRenderState;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.crown.PigRenderStateAccessor;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererStateMixin {

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/passive/PigEntity;Lnet/minecraft/client/render/entity/state/PigEntityRenderState;F)V",
            at = @At("TAIL"))
    private void captureArmorState(PigEntity pig, PigEntityRenderState state, float tickDelta, CallbackInfo ci) {
        ((PigRenderStateAccessor)state).arloTheLittleGuy$setBodyArmor(pig.getBodyArmor());
    }
}