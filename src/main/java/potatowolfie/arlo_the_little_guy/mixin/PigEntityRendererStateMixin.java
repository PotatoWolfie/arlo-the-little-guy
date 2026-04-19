package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.state.PigRenderState;
import net.minecraft.world.entity.animal.pig.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.crown.PigRenderStateAccessor;

@Mixin(PigRenderer.class)
public class PigEntityRendererStateMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/animal/pig/Pig;Lnet/minecraft/client/renderer/entity/state/PigRenderState;F)V",
            at = @At("TAIL"))
    private void captureArmorState(Pig pig, PigRenderState state, float tickDelta, CallbackInfo ci) {
        ((PigRenderStateAccessor)state).arloTheLittleGuy$setBodyArmor(pig.getBodyArmorItem());
    }
}