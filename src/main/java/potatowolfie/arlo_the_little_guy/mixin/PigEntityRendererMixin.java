package potatowolfie.arlo_the_little_guy.mixin;

import net.minecraft.client.model.animal.pig.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PigRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import potatowolfie.arlo_the_little_guy.entity.client.ModEntityModelLayers;
import potatowolfie.arlo_the_little_guy.entity.crown.LivingEntityRendererAccessor;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownModel;
import potatowolfie.arlo_the_little_guy.entity.crown.PigCrownRenderer;

@Mixin(PigRenderer.class)
public class PigEntityRendererMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addCrownFeature(EntityRendererProvider.Context context, CallbackInfo ci) {
        LivingEntityRendererAccessor<PigRenderState, PigModel> accessor =
                (LivingEntityRendererAccessor<PigRenderState, PigModel>) (Object) this;

        accessor.arloTheLittleGuy$addFeature(new PigCrownRenderer(
                (RenderLayerParent<PigRenderState, PigModel>) (Object) this,
                new PigCrownModel(context.bakeLayer(ModEntityModelLayers.PIG_CROWN))
        ));
    }
}